# from ultralytics import YOLO
# from PIL import Image
import os
import cv2
import numpy as np
from collections import OrderedDict
import torch
from models.model import GLPDepth
import torchvision.transforms as transforms
from visionUtils.utils.utils import time_synchronized
import time

class DepthModel:
    def __init__(self,args,device="cuda"):
        self.args = args
        self.device = device
        self.model = GLPDepth(self.args).to(self.device)
        model_weight = torch.load(args.ckpt_dir)
        if 'module' in next(iter(model_weight.items()))[0]:
            model_weight = OrderedDict((k[7:], v) for k, v in model_weight.items())
        self.model.load_state_dict(model_weight) 
        self.transform = transforms.ToTensor()
    def __call__(self,img,save=False):
        if img.shape[:2] != (192,320):
            img = cv2.resize(img,(320,192)) # resize the image to accelerate
        img =  self.transform(img).unsqueeze(0).to(self.device)
        self.model.eval()
        with torch.no_grad():
            t1 = time.time()
            pred = self.model(img)
            t2 = time.time()
            print(f"\033[95m*****************depth inf:{t2-t1:.3f}s/frame*****************\033[0m")
        pred_d = pred['pred_d']

        if self.args.save_eval_pngs or save:
            save_path = os.path.join(self.args.result_dir, "depth.jpg")
            if save_path.split('.')[-1] == 'jpg':
                save_path = save_path.replace('jpg', 'png')
            pred_d = pred_d.squeeze()
            if self.args.dataset == 'nyudepthv2':
                pred_d = pred_d.cpu().numpy() * 1000.0
                cv2.imwrite(save_path, pred_d.astype(np.uint16),
                            [cv2.IMWRITE_PNG_COMPRESSION, 0])
            else:
                pred_d = pred_d.cpu().numpy() * 256.0
                cv2.imwrite(save_path, pred_d.astype(np.uint16),
                            [cv2.IMWRITE_PNG_COMPRESSION, 0])
        
        pred_d_numpy = pred_d.cpu().squeeze().numpy()
        pred_d_numpy = (pred_d_numpy / np.nanmax(pred_d_numpy)) * 255
        pred_d_numpy = pred_d_numpy.astype(np.uint8)
        pred_d_color = cv2.applyColorMap(pred_d_numpy, cv2.COLORMAP_RAINBOW)
        if self.args.save_visualize or save:
            save_path = os.path.join(self.args.result_dir, "depth_visualize.jpg")
            cv2.imwrite(save_path, pred_d_color)
            
        return pred_d_color,pred_d.cpu()