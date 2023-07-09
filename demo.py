import os
# import sys
import cv2
import copy
import time

# import multipress tool
import queue
import multiprocessing

# import uitil tools
import torch
import numpy as np
# from PIL import Image

# import model
from ultralytics import YOLO
from depth_model import DepthModel
from visionUtils.videoDemo import detectModel

# import other tools
import torch.backends.cudnn as cudnn
from configs.test_options import TestOptions
# from visionUtils.utils.utils import  \
#     time_synchronized, time_synchronized,  non_max_suppression_without_ancher, \
#     scale_coords, non_max_suppression, split_for_trace_model, select_device, \
#     driving_area_mask, lane_line_mask, plot_one_box, show_seg_result 

def read_image():
    img = cv2.imread("kitti/img/b1d0a191-03dcecc2.jpg")   
    return img
class ImageProcessor(multiprocessing.Process):
    def __init__(self, model_cls, args, img_queue, model_name, results):
        multiprocessing.Process.__init__(self)
        self.img_queue = img_queue 
        self.model_cls = model_cls
        self.args = args
        self.model_name = model_name
        self.results = results  
    def run(self):
        print(f"load model{self.model_name}")
        self.model = self.model_cls(self.args)
        # inference
        while True:
            try:
                frame_index, img = self.img_queue.get(timeout=1)  # 等待图像，超时时间为1秒
            except queue.Empty:
                continue  # 如果队列为空，就继续等待
                pass
            torch.cuda.empty_cache() 
            self.results[self.model_name] = self.model(img)

def image_reader(img_queue1, img_queue2, img_queue3):
    frame_index = 0
    while True:
        img = read_image()  # 你的图像读取逻辑
        img = cv2.resize(img,(1280,720))
        img0 = cv2.resize(img,(640,384)) 
        img1 = copy.deepcopy(img)
        img2 = copy.deepcopy(img0)
        img3 = copy.deepcopy(img0)
        img_queue1.put((frame_index, img1))
        img_queue2.put((frame_index, img2))
        img_queue3.put((frame_index, img3))
        frame_index += 1

def main():
    manager = multiprocessing.Manager()
    # perss arguments 
    opt = TestOptions()
    args = opt.initialize().parse_args()
    if args.gpu_or_cpu == 'gpu':
        device = torch.device('cuda')
        cudnn.benchmark = True
    else:
        device = torch.device('cpu')
    if args.save_eval_pngs or args.save_visualize:
        result_path = os.path.join(args.result_dir, args.exp_name)
        print("Saving result images in to %s" % result_path)

    results = manager.dict()

    img_queue1 = multiprocessing.Queue()
    img_queue2 = multiprocessing.Queue()
    img_queue3 = multiprocessing.Queue()
    # 创建线程对象
    reader_process = multiprocessing.Process(target=image_reader, args=(img_queue1, img_queue2, img_queue3,))
    lane_process = ImageProcessor(detectModel, "visionUtils/data/weights/yolopv2.pt", img_queue1, "Lane Model", results)
    yolo_process = ImageProcessor(YOLO, "ckpt/best.pt", img_queue2, "YOLO Model", results)
    depth_process = ImageProcessor(DepthModel, args, img_queue3, "Depth Model", results)

    reader_process.start()
    lane_process.start()
    yolo_process.start()
    depth_process.start()

    # # 等待所有进程完成
    # lane_process.join()
    # yolo_process.join()
    # depth_process.join()

    # 获取处理结果
    img = read_image()
    while True:
        try:
            lane_result = results["Lane Model"]
            yolo_result = results["YOLO Model"]
            depth_result = results["Depth Model"]
        except:
            time.sleep(0.1)
    
    # show_depth(depth_result,yolo_result,img)

def show_depth(depth_result,yolo_result,img):
    opencv_image = img 
    
    # prepare show parameters
    image_height, image_width = opencv_image.shape[:2]  
    font = cv2.FONT_HERSHEY_SIMPLEX
    font_scale = 0.3
    font_thickness = 1

    # show predict results
    for i, bbox in enumerate(yolo_result.boxes.xyxyn):
        absolute_bbox = [
            int(bbox[0] * image_width),
            int(bbox[1] * image_height),
            int(bbox[2] * image_width),
            int(bbox[3] * image_height)
        ]

        mid_x = int(round((absolute_bbox[0] + absolute_bbox[2]) / 2)/2)
        mid_y = int(round((absolute_bbox[1] + absolute_bbox[3]) / 2)/2)
        depth = depth_result[1].squeeze()[mid_y,mid_x] if not np.isnan(depth_result[1].squeeze()[mid_y,mid_x]) else 0
        label = f"c {int(yolo_result.boxes.cls[i].item())},d {int(depth)}"
        (text_width, text_height), _ = cv2.getTextSize(label, font, font_scale, font_thickness)

        text_offset_x = int(bbox[0] * image_width)
        text_offset_y = int(bbox[1] * image_height) - 5
        cv2.rectangle(opencv_image, (absolute_bbox[0], absolute_bbox[1]), (absolute_bbox[2], absolute_bbox[3]), (0,255,0), 2)
        cv2.putText(opencv_image, label, (text_offset_x, text_offset_y), font, font_scale, (255, 255, 255), font_thickness, lineType=cv2.LINE_AA)
    cv2.imwrite("image.jpg", opencv_image)
    return opencv_image
    
if __name__ == "__main__":
    multiprocessing.set_start_method('spawn')
    main()