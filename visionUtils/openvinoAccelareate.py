import numpy as np
import cv2
import torch 
import torchvision
import math
import time
from PIL import Image
from openvino.runtime import Core
import matplotlib.pyplot as plt

import torchvision.transforms as transforms



model_path = 'visionUtils/data/onnx/yolop-640-640.onnx'
image_path = '/home/linda/Documents/project/softWareCup/YOLOPv2-main/data/example.jpg'

img_pil = Image.open(image_path)
img_pil = np.array(img_pil)
# Letterbox
img0 = img_pil.copy()

h0, w0 = img0[0].shape[:2]
img, _, pad = letterbox_for_img(img0, 640)

# Stack
h, w = img.shape[:2]
shapes = (h0, w0), ((h / h0, w / w0), pad)

# Convert
#img = img[..., ::-1].transpose((0, 3, 1, 2))  # BGR to RGB, BHWC to BCHW
img = np.ascontiguousarray(img)
img = img.astype('float32')
img /= 255.0

# 设置所需的尺寸
target_height, target_width = 640, 640

# 计算需要填充的边缘
pad_top = (target_height - img.shape[0]) // 2
pad_bottom = target_height - img.shape[0] - pad_top
pad_left = (target_width - img.shape[1]) // 2
pad_right = target_width - img.shape[1] - pad_left

# 使用黑色 (0, 0, 0) 对图像进行填充
padded_image = cv2.copyMakeBorder(img, pad_top, pad_bottom, pad_left, pad_right, cv2.BORDER_CONSTANT, value=(0, 0, 0))




normalize = transforms.Normalize(
        mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]
    )
transform=transforms.Compose([
            transforms.ToTensor(),
            normalize,
        ])

input_image = transform(padded_image)

input_image = np.expand_dims(
   input_image, 0
)
# 显示图像

# input_image = np.transpose(input_image[0], (1, 2, 0))
# input_image = np.clip(input_image, 0, 255) / 255.0
# plt.imshow(np.transpose(input_image[0], (1, 2, 0)))
# plt.imshow(input_image)
plt.show()

ie = Core()
model = ie.read_model(model=model_path)
compiled_model = ie.compile_model(model, "AUTO")

input_layer_ir = compiled_model.input(0)
N, C, H, W = input_layer_ir.shape
# resized_image = img_pil.resize(640,384)
print(compiled_model.outputs)

output_layer_names = ['det_out', 'drive_area_seg', 'lane_line_seg']
outputs = compiled_model(input_image)
# 取出数据编成字典
output_data = {output_layer_name: outputs[compiled_model.output(output_layer_name)] 
               for output_layer_name in output_layer_names }
det_out = output_data['det_out']
seg_out = output_data['drive_area_seg']
lan_out = output_data['lane_line_seg']





# inf_out, _ = det_out
# Apply NMS
# det_pred = non_max_suppression(inf_out, conf_thres=0.7, iou_thres=0.5, classes=None, agnostic=False)
# det=det_pred[0]

# da_seg_mask = torch.nn.functional.interpolate(da_predict, scale_factor=int(1/ratio), mode='bilinear')
# _, da_seg_mask = torch.max(da_seg_mask, 1)
# da_seg_mask = da_seg_mask.int().squeeze().cpu().numpy()

# ll_predict = ll_seg_out[:, :,pad_h:(height-pad_h),pad_w:(width-pad_w)]
# ll_seg_mask = torch.nn.functional.interpolate(ll_predict, scale_factor=int(1/ratio), mode='bilinear')
# _, ll_seg_mask = torch.max(ll_seg_mask, 1)
# ll_seg_mask = ll_seg_mask.int().squeeze().cpu().numpy()

# img0 = show_seg_result(img0, (da_seg_mask, ll_seg_mask), _, _, is_demo=True)
# if len(det):
#             det[:,:4] = scale_coords(img.shape[2:],det[:,:4],img0.shape).round()
#             for *xyxy,conf,cls in reversed(det):
#                 label_det_pred = f'{conf:.2f}'
#                 plot_one_box(xyxy, img0 , label=label_det_pred, color=[255,0,0], line_thickness=2)


# segmentation_mask = np.argmax(det_out, axis=1)
# plt.imshow(segmentation_mask.transpose(1, 2, 0))
