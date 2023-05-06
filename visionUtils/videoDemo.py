import argparse
import cv2
import torch
import numpy as np
from pathlib import Path
import torchvision.transforms as transforms
from utils.utils import select_device,LoadImages
# Conclude setting / general reprocessing / plots / metrices / datasets
from utils.utils import \
    time_synchronized,select_device,normalize_imge, \
    scale_coords,non_max_suppression,split_for_trace_model,\
    driving_area_mask,lane_line_mask,plot_one_box,show_seg_result,\
    AverageMeter,\
    LoadImages
    
from openvino.runtime import Core
    
class detectModel():
    def __init__(self,model_path='visionUtils/data/weights/yolopv2.pt',device = '0') -> None:
        """generate a model detector

        Args:
            - model_path (str, optional): the path of model. Defaults to 'visionUtils/data/weights/yolopv2.pt'.
            - device (str, optional): the device to select cpu/cuda.... Defaults to '0'(for cuda).
        """
        # Load model
        self.device = device
        self.model  = torch.jit.load(model_path)
        device = select_device(device)
        half = device.type != 'cuda'  # half precision only supported on CUDA
        self.model = self.model.to(device)

        if half:
            self.model.half()  # to FP16  
        self.model.eval()

    def generate_detection(self, img):
        """read video and draw traffic object detection, drivable road area segmenta-
            tion and lane detection results 

        Args:
            - video (str): the path of video
            - model_path (str): the path of model
        """
        # img0 = cv2.imread(vid_path)  # BGR
        img0 = img
        img0 = cv2.resize(img0, (1280,720), interpolation=cv2.INTER_LINEAR)
        img = letterbox(img0)[0]
        # Convert
        img = img[:, :, ::-1].transpose(2, 0, 1)  # BGR to RGB, to 3x416x416
        img = np.ascontiguousarray(img)
        img = torch.from_numpy(img).to(self.device)
        img = img.half() if self.device != 'cpu' else img.float()  # uint8 to fp16/32
        img /= 255.0  # 0 - 255 to 0.0 - 1.0
        
        inf_time = AverageMeter()
        waste_time = AverageMeter()
        nms_time = AverageMeter()
        
        if img.ndimension() == 3:
            img = img.unsqueeze(0)

        # Inference
        with torch.inference_mode():
            t1 = time_synchronized()
            [pred,anchor_grid],seg,ll= self.model(img)
            t2 = time_synchronized()
        
        tw1 = time_synchronized()
        pred = split_for_trace_model(pred,anchor_grid)
        tw2 = time_synchronized()
        
        # Apply NMS
        t3 = time_synchronized()
        pred = non_max_suppression(pred, conf_thres = 0.5, iou_thres = 0.5, classes=None, agnostic=False)
        t4 = time_synchronized()
        
        da_seg_mask = driving_area_mask(seg)
        ll_seg_mask = lane_line_mask(ll)
        
        for i, det in enumerate(pred):  # detections per image
            s = '%gx%g ' % img.shape[2:]  # print string
            gn = torch.tensor(img0.shape)[[1, 0, 1, 0]]  # normalization gain whwh
            if len(det):
                # Rescale boxes from img_size to im0 size
                det[:, :4] = scale_coords(img.shape[2:], det[:, :4], img0.shape).round()
            # show object detection
            for *xyxy, conf, cls in reversed(det):
                plot_one_box(xyxy, img0, line_thickness=3)
            # Print time (inference)
            print(f'{s}Done. ({t2 - t1:.3f}s)')
            show_seg_result(img0, (da_seg_mask,ll_seg_mask), is_demo=True)
        inf_time.update(t2-t1,img.size(0))
        nms_time.update(t4-t3,img.size(0))
        waste_time.update(tw2-tw1,img.size(0))
        print('inf : (%.4fs/frame)   nms : (%.4fs/frame)' % (inf_time.avg,nms_time.avg))
        return img0    
    
class vinoModel():
    def __init__(self,model_path) -> None:
        # 使用openvino加载模型加速在cpu上的推理速度
        ie = Core()
        model = ie.read_model(model=model_path)
        self.compiled_model = ie.compile_model(model, "AUTO")
        input_layer_ir = self.compiled_model.input(0)
        self.det_out_ir = self.compiled_model.output("det_out")
        self.seg_out_ir = self.compiled_model.output("drive_area_seg")
        self.lan_out_ir = self.compiled_model.output("lane_line_seg")
    def detect(self,image):
        """返回模型的目标检测，和语义分割的结果，目前是一个图片

        Args:
            - img (cv.Map): 输入要求是一个图片
        """
        img0 = cv2.resize(image, (1280,720), interpolation=cv2.INTER_LINEAR)
        img = letterbox(img0)[0]
        
        # Convert
        # img = normalize_imge(img)
        normalize = transforms.Normalize(mean=[0.485, 0.456, 0.406],
                                         std=[0.229, 0.224, 0.225])
        transform=transforms.Compose([transforms.ToTensor(),
                                      normalize])
        img = transform(img)
        img = np.expand_dims(img, 0)
        
        inf_time = AverageMeter()
        nms_time = AverageMeter()
            
        
        
        # inference
        t1 = time_synchronized()
        preds = self.compiled_model(img)
        t2 = time_synchronized()
        
        # Apply NMS
        t3 = time_synchronized()
        # pred = non_max_suppression(pred, conf_thres = 0.5, iou_thres = 0.5, classes=None, agnostic=False)
        t4 = time_synchronized()
        
        det_out = preds[self.det_out_ir]  # det_out
        seg_out = torch.from_numpy(preds[self.seg_out_ir])  # drive_area_seg
        lan_out = torch.from_numpy(preds[self.lan_out_ir])  # lane_line_seg
        
        da_seg_mask = driving_area_mask(seg_out)
        ll_seg_mask = lane_line_mask(lan_out)
        show_seg_result(img0, (da_seg_mask,ll_seg_mask), is_demo=True)
        
        inf_time.update(t2-t1,img.shape[0])
        nms_time.update(t4-t3,img.shape[0])
        print('inf : (%.4fs/frame)   nms : (%.4fs/frame)' % (inf_time.avg,nms_time.avg))
        return img0



    
    
def letterbox(img, new_shape=(640, 640), color=(114, 114, 114), auto=True, scaleFill=False, scaleup=True, stride=32):
    # Resize and pad image while meeting stride-multiple constraints
    shape = img.shape[:2]  # current shape [height, width]
    if isinstance(new_shape, int):
        new_shape = (new_shape, new_shape)
    #print(sem_img.shape)
    # Scale ratio (new / old)
    r = min(new_shape[0] / shape[0], new_shape[1] / shape[1])

    if not scaleup:  # only scale down, do not scale up (for better test mAP)
        r = min(r, 1.0)

    # Compute padding
    ratio = r, r  # width, height ratios
    new_unpad = int(round(shape[1] * r)), int(round(shape[0] * r))
    dw, dh = new_shape[1] - new_unpad[0], new_shape[0] - new_unpad[1]  # wh padding
    if auto:  # minimum rectangle
        dw, dh = np.mod(dw, stride), np.mod(dh, stride)  # wh padding
    elif scaleFill:  # stretch
        dw, dh = 0.0, 0.0
        new_unpad = (new_shape[1], new_shape[0])
        ratio = new_shape[1] / shape[1], new_shape[0] / shape[0]  # width, height ratios

    dw /= 2  # divide padding into 2 sides
    dh /= 2

    if shape[::-1] != new_unpad:  # resize
        img = cv2.resize(img, new_unpad, interpolation=cv2.INTER_LINEAR)
     
    top, bottom = int(round(dh - 0.1)), int(round(dh + 0.1))
    left, right = int(round(dw - 0.1)), int(round(dw + 0.1))

    img = cv2.copyMakeBorder(img, top, bottom, left, right, cv2.BORDER_CONSTANT, value=color)  # add border
    
    return img, ratio, (dw, dh)
if __name__=='__main__':
    model_path = 'visionUtils/data/onnx/yolop-384-640.onnx'
    image_path = '/home/linda/Documents/project/softWareCup/YOLOPv2-main/data/example.jpg'
    model = vinoModel(model_path)
    from PIL import Image
    img_pil = Image.open(image_path)
    img_np = np.array(img_pil)
    img0 = model.detect(img_np)
    img_pil = Image.fromarray(np.uint8(img0))
    img_pil.show()