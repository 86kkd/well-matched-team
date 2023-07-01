from ultralytics import YOLO
from PIL import Image
import os
import cv2
import numpy as np
from collections import OrderedDict

import torch
from torch.utils.data import DataLoader
import torch.backends.cudnn as cudnn

import utils.logging as logging
import utils.metrics as metrics
from models.model import GLPDepth
from dataset.base_dataset import get_dataset
from configs.test_options import TestOptions
import torchvision.transforms as transforms

metric_name = ['d1', 'd2', 'd3', 'abs_rel', 'sq_rel', 'rmse', 'rmse_log',
               'log10', 'silog']

yolo_model = YOLO("ckpt/best.pt")
image_url = "datasets/bdd100k/val"
img = Image.open("kitti/img/b1d0a191-03dcecc2.jpg")
det_results = yolo_model(img,save=True)  # 预测图像并保存
opt = TestOptions()
args = opt.initialize().parse_args()
print(args)

if args.gpu_or_cpu == 'gpu':
    device = torch.device('cuda')
    cudnn.benchmark = True
else:
    device = torch.device('cpu')

if args.save_eval_pngs or args.save_visualize:
    result_path = os.path.join(args.result_dir, args.exp_name)
    logging.check_and_make_dirs(result_path)
    print("Saving result images in to %s" % result_path)

if args.do_evaluate:
    result_metrics = {}
    for metric in metric_name:
        result_metrics[metric] = 0.0

print("\n1. Define Model")
model = GLPDepth(args=args).to(device)

input_RGB = img.resize((640,384))
transform = transforms.ToTensor()
input_RGB =  transform(input_RGB).unsqueeze(0).to('cuda')
filename = "test.png"

model_weight = torch.load(args.ckpt_dir)
if 'module' in next(iter(model_weight.items()))[0]:
    model_weight = OrderedDict((k[7:], v) for k, v in model_weight.items())
model.load_state_dict(model_weight)
model.eval()
with torch.no_grad():
    if args.shift_window_test:
        bs, _, h, w = input_RGB.shape
        assert w > h and bs == 1
        interval_all = w - h
        interval = interval_all // (args.shift_size-1)
        sliding_images = []
        sliding_masks = torch.zeros((bs, 1, h, w), device=input_RGB.device) 
        for i in range(args.shift_size):
            sliding_images.append(input_RGB[..., :, i*interval:i*interval+h])
            sliding_masks[..., :, i*interval:i*interval+h] += 1
        input_RGB = torch.cat(sliding_images, dim=0)
    if args.flip_test:
        # input_RGB_cpu = input_RGB.cpu().numpy()[0]
        input_RGB = torch.cat((input_RGB, torch.flip(input_RGB, [3])), dim=0)
        pred = model(input_RGB)
pred_d = pred['pred_d']
print("finish inference")
if args.flip_test:
    batch_s = pred_d.shape[0]//2
    pred_d = (pred_d[:batch_s] + torch.flip(pred_d[batch_s:], [3]))/2.0
if args.shift_window_test:
    pred_s = torch.zeros((bs, 1, h, w), device=pred_d.device)
    for i in range(args.shift_size):
        pred_s[..., :, i*interval:i*interval+h] += pred_d[i:i+1]
    pred_d = pred_s/sliding_masks


if args.save_eval_pngs:
    save_path = os.path.join(result_path, filename)
    if save_path.split('.')[-1] == 'jpg':
        save_path = save_path.replace('jpg', 'png')
    pred_d = pred_d.squeeze()
    if args.dataset == 'nyudepthv2':
        pred_d = pred_d.cpu().numpy() * 1000.0
        cv2.imwrite(save_path, pred_d.astype(np.uint16),
                    [cv2.IMWRITE_PNG_COMPRESSION, 0])
    else:
        pred_d = pred_d.cpu().numpy() * 256.0
        cv2.imwrite(save_path, pred_d.astype(np.uint16),
                    [cv2.IMWRITE_PNG_COMPRESSION, 0])
    
if args.save_visualize:
    save_path = os.path.join(result_path, filename)
    pred_d_numpy = pred_d.squeeze()
    pred_d_numpy = (pred_d_numpy / np.nanmax(pred_d_numpy)) * 255
    pred_d_numpy = pred_d_numpy.astype(np.uint8)
    pred_d_color = cv2.applyColorMap(pred_d_numpy, cv2.COLORMAP_RAINBOW)
    cv2.imwrite(save_path, pred_d_color)


image_array = np.array(img.resize((640,384)))
opencv_image = cv2.cvtColor(image_array, cv2.COLOR_RGB2BGR)
image_height, image_width = opencv_image.shape[:2]  
font = cv2.FONT_HERSHEY_SIMPLEX
font_scale = 0.3
font_thickness = 1

for i, bbox in enumerate(det_results[0].boxes.xyxyn):
    absolute_bbox = [
        int(bbox[0] * image_width),
        int(bbox[1] * image_height),
        int(bbox[2] * image_width),
        int(bbox[3] * image_height)
    ]
    
    
    mid_x = int(round((absolute_bbox[0] + absolute_bbox[2]) / 2))
    mid_y = int(round((absolute_bbox[1] + absolute_bbox[3]) / 2))
    depth = pred_d.squeeze()[mid_y,mid_x]
    label = f"c {int(det_results[0].boxes.cls[i].item())},d {int(depth)}"
    (text_width, text_height), _ = cv2.getTextSize(label, font, font_scale, font_thickness)

    text_offset_x = int(bbox[0] * image_width)
    text_offset_y = int(bbox[1] * image_height) - 5
    cv2.rectangle(opencv_image, (absolute_bbox[0], absolute_bbox[1]), (absolute_bbox[2], absolute_bbox[3]), (0,255,0), 2)
    cv2.putText(opencv_image, label, (text_offset_x, text_offset_y), font, font_scale, (255, 255, 255), font_thickness, lineType=cv2.LINE_AA)
cv2.imwrite("image.jpg", opencv_image)
