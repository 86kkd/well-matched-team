# from ultralytics import YOLO
# from PIL import Image
import os
import cv2
import numpy as np
from collections import OrderedDict
import torch.nn as nn
import torch
# from torch.utils.data import DataLoader
# import torch.backends.cudnn as cudnn

# import utils.logging as logging
# import utils.metrics as metrics
from models.model import GLPDepth
# from dataset.base_dataset import get_dataset
# from configs.test_options import TestOptions
import torchvision.transforms as transforms
# from visionUtils.videoDemo import vinoModel,detectModel
from visionUtils.utils.utils import time_synchronized

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
            bs, _, h, w = img.shape
            assert w > h and bs == 1
            interval_all = w - h
            interval = interval_all // (self.args.shift_size-1)
            sliding_images = []
            sliding_masks = torch.zeros((bs, 1, h, w), device=img.device) 
            for i in range(self.args.shift_size):
                sliding_images.append(img[..., :, i*interval:i*interval+h])
                sliding_masks[..., :, i*interval:i*interval+h] += 1
            img = torch.cat(sliding_images, dim=0)
            img = torch.cat((img, torch.flip(img, [3])), dim=0)
            t1 = time_synchronized()
            pred = self.model(img)
            t2 = time_synchronized()
            print(f"\033[95m*****************depth inf:{t2-t1:.3f}s/frame*****************\033[0m")
        pred_d = pred['pred_d']
        batch_s = pred_d.shape[0]//2
        pred_d = (pred_d[:batch_s] + torch.flip(pred_d[batch_s:], [3]))/2.0
        pred_s = torch.zeros((bs, 1, h, w), device=pred_d.device)
        for i in range(self.args.shift_size):
            pred_s[..., :, i*interval:i*interval+h] += pred_d[i:i+1]
        pred_d = pred_s/sliding_masks


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