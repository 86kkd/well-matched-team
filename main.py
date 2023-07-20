from collections.abc import Callable, Iterable, Mapping
import os
from typing import Any
from io import BytesIO
from PIL import ImageFile
# import sys
import cv2
import copy
import time
import socket
import base64
from PIL import Image
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
from visionUtils.line_detection_utils import *
from visionUtils.utils.utils import  \
    time_synchronized, time_synchronized,  non_max_suppression_without_ancher, \
    scale_coords, non_max_suppression, split_for_trace_model, select_device, \
    driving_area_mask, lane_line_mask, plot_one_box, show_seg_result 
ImageFile.LOAD_TRUNCATED_IMAGES = True


class VideoReader:
    def __init__(self, video_path):
        self.cap = cv2.VideoCapture(video_path)
        if not self.cap.isOpened():
            print("Error opening video file")

    def read_frame(self):
        if self.cap.isOpened():
            ret, frame = self.cap.read()
            if ret:
                return frame
            else:
                print("End of video")
                return None
        else:
            print("Video file not open")
            return None

    def close(self):
        if self.cap.isOpened():
            self.cap.release()
reader = VideoReader("video/cca96d47-9ea3e639.mov")

def read_image(socket_queue):
    # img = cv2.imread("kitti/img/b1d0a191-03dcecc2.jpg")  
    # img = reader.read_frame()
    while True:
        try:
            img = socket_queue.get(timeout=1)
            break
        except:
            time.sleep(0.1)
            print(f"\033[93m等待输入\033[0m")
    return img

def encoder_alone(det_img):
        image = Image.fromarray(det_img)
        # 将PIL Image对象转换为字节数据
        buffer = BytesIO()
        image.save(buffer, format='JPEG')
        image_data = buffer.getvalue()
        # 将字节数据编码为base64字符串
        base64_image_data = base64.b64encode(image_data).decode('utf-8')

        return base64_image_data


class SocketServer(multiprocessing.Process):
  
    def __init__(self, socket_queue):
        multiprocessing.Process.__init__(self)
        self.socket_queue = socket_queue
    
    def decoder(self, image_data):
        # 对字节数据进行解码
        print("decode1")
        decoded_image_data = base64.b64decode(image_data)
        image = Image.open(BytesIO(decoded_image_data))
        img_np = np.array(image)
        # image.show()
        print("decode done")
        return img_np
    def encoder(self, det_img):
        image = Image.fromarray(det_img)
        # 将PIL Image对象转换为字节数据
        buffer = BytesIO()
        image.save(buffer, format='JPEG')
        image_data = buffer.getvalue()
        # 将字节数据编码为base64字符串
        base64_image_data = base64.b64encode(image_data).decode('utf-8')

        return base64_image_data
    
    
    def run(self):
        HOST = '172.17.0.2'  # 监听的地址
        PORT = 8888  # 监听的端口号
        # 创建套接字并监听
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind((HOST, PORT))
        server_socket.listen(1)
        while True:
            try:
                # 等待客户端连接
                print('Waiting for Java client...')
                client_socket, address = server_socket.accept()
                print('Connected by', address)
                client_socket.settimeout(0.5)
                # 接收Java客户端发送的数据并返回
                result = ""
                while True:
                    data = client_socket.recv(1024)
                    temp_data = data.decode()
                    if "#" in temp_data:
                        temp_data= temp_data.replace("#","")
                        result += temp_data
                        break
                    result += temp_data
                print('Received from Java client' + str(time.time()))
                self.socket_queue.put(self.decoder(result))
                print("encode" + str(time.time()))
                result1 = "ok".encode()
                print("send" + str(time.time()))
                client_socket.send(result1)
                print("done" + str(time.time()))
                client_socket.close()

            except Exception as err:
                server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                server_socket.bind((HOST, PORT))
                server_socket.listen(1)
                print(err)
                continue
    def _closed():
        pass
    
    

class ImageProcessor(multiprocessing.Process):
    def __init__(self, model_cls, args, img_queue, model_name, results):
        multiprocessing.Process.__init__(self)
        self.img_queue = img_queue 
        self.model_cls = model_cls
        self.args = args
        self.model_name = model_name
        self.results = results  
    def run(self):
        print(f"load \033[92m{self.model_name}\033[0m")
        self.model = self.model_cls(self.args)
        # inference
        while True:
            try:
                frame_index, img = self.img_queue.get(timeout=1)  # 等待图像，超时时间为1秒
                print("\033[92mget frame\033[0m")
            except queue.Empty:
                continue  # 如果队列为空，就继续等待
            self.results[(self.model_name,frame_index)] = self.model(img)
            torch.cuda.empty_cache()

def image_reader(socket_queue,img_queue,img_queue1, img_queue2, img_queue3):
    frame_index = 0
    while True:
        img = read_image(socket_queue)  # 你的图像读取逻辑
        img = cv2.resize(img,(1280,720))
        img0 = cv2.resize(img,(640,384)) 
        img1 = copy.deepcopy(img)
        img2 = copy.deepcopy(img0)
        img3 = copy.deepcopy(img0)
        img_queue.put((frame_index,img))
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
    
    socket_queue = multiprocessing.Queue(maxsize=10)
    img_queue = multiprocessing.Queue(maxsize=10)
    img_queue1 = multiprocessing.Queue(maxsize=10)
    img_queue2 = multiprocessing.Queue(maxsize=10)
    img_queue3 = multiprocessing.Queue(maxsize=10)
    # 创建线程对象
    reader_process = multiprocessing.Process(target=image_reader, args=(socket_queue,img_queue, img_queue1, img_queue2, img_queue3,))
    lane_process = ImageProcessor(detectModel, "visionUtils/data/weights/yolopv2.pt", img_queue1, "Lane Model", results)
    yolo_process = ImageProcessor(YOLO, "ckpt/best.pt", img_queue2, "YOLO Model", results)
    depth_process = ImageProcessor(DepthModel, args, img_queue3, "Depth Model", results)
    socket_read_process = SocketServer(socket_queue)
    
    reader_process.start()
    lane_process.start()
    yolo_process.start()
    depth_process.start()
    socket_read_process.start()

    fourcc = cv2.VideoWriter_fourcc(*'mp4v')  # 使用 mp4v 编码器
    out1 = cv2.VideoWriter('output.mp4', fourcc, 30, (1280, 720))
    out2 = cv2.VideoWriter('depth.mp4', fourcc, 30, (320, 192))
    out3 = cv2.VideoWriter('bired.mp4', fourcc, 30, (1280, 720))
    frame_index = 0
    while True:
        pass
        try:
            lane_result = results[("Lane Model",frame_index)]
            yolo_result = results[("YOLO Model",frame_index)]
            depth_result = results[("Depth Model",frame_index)]
            index , img = img_queue.get(timeout=1)
            assert index == frame_index, "\033[92m处理帧和队列返回帧不匹配\033[0m"
            frame_index += 1
            print(f"get results{frame_index}")
            
            img, depth_color, bird_view, red_light, pedestrian = show_result(depth_result,yolo_result,lane_result,img)

            while True:
                try:
                    host = '10.33.35.220'
                    port = 5000
                    client = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
                    client.connect((host,port))
                    
                    data = encoder_alone(img) + '#' + encoder_alone(depth_color) + '#' + encoder_alone(bird_view) + '#' + str(red_light) + '#' + str(pedestrian) + '#'
                    client.send(data.encode())
                    client.close()                    
                    break
                except:
                    time.sleep(0.1)
                
            out1.write(img)
            out2.write(depth_color)
            out3.write(bird_view)
        except KeyboardInterrupt:
            out1.release() 
            out2.release() 
            out3.release() 
            print("Ctrl+C pressed, exiting loop.")
            break
        except:
            time.sleep(0.05)


def show_result(depth_result,yolo_result,lane_result,img):
    red_light, pedestrian = 0, False
    
    depth_color,pred_d = depth_result
    [pred, anchor_grid], seg, ll = lane_result
    # prepare show parameters
    img_h, img_w = img.shape[:2]  
    font = cv2.FONT_HERSHEY_SIMPLEX
    font_scale = 0.8
    font_thickness = 1
    da_seg_mask = driving_area_mask(seg)
    ll_seg_mask = lane_line_mask(ll)
    
    try :
        offset, left_param, right_param, cut_line_d = evaluate_offset(ll_seg_mask)
        axi_y = np.linspace(cut_line_d, img.shape[0],img.shape[0]-cut_line_d +1)
        left_lane = left_param[0]*axi_y**2 + left_param[1]*axi_y + left_param[2]
        right_lane = right_param[0]*axi_y**2 + right_param[1]*axi_y + right_param[2]
        print(f"offset:{offset}") # if x > 1*10^4 it must be really offset 
        # for i in range(len(axi_y)):
            # cv2.circle(img, (int(left_lane[i]), int(axi_y[i])), radius=1, color=(255, 0, 0), thickness=2)
            # cv2.circle(img, (int(right_lane[i]), int(axi_y[i])), radius=1, color=(255, 0, 0), thickness=2)
    except:
        pass
    
    bird_view = warper(img,M_b) 
    
    for i, det in enumerate(pred):  # detections per image
        if len(det):
            # Rescale boxes from img_size to im0 size
            det[:, :4] = scale_coords((384,640), det[:, :4], img.shape).round()
        # show object detection
        for *xyxy, conf, cls in reversed(det):
            plot_one_box(xyxy, pred_d.squeeze()*2, img, line_thickness=3)
        show_seg_result(img, (da_seg_mask, ll_seg_mask), is_demo=True)
            
    for i, bbox in enumerate(yolo_result.boxes.xyxyn):
        
            
        
        rel_box = [
            int(bbox[0] * img_w),
            int(bbox[1] * img_h),
            int(bbox[2] * img_w),
            int(bbox[3] * img_h)
        ]

        mid_x = int(round((rel_box[0] + rel_box[2]) / 2)/4)
        mid_y = int(round((rel_box[1] + rel_box[3]) / 2)/4)
        depth = (pred_d.squeeze()*2)[mid_y,mid_x] 
        
        if not np.isnan(depth):
            label = f"d:{int(depth)}"
            (text_width, text_height), _ = cv2.getTextSize(label, font, font_scale, font_thickness)
            text_offset_x = int(bbox[0] * img_w)
            text_offset_y = int(bbox[1] * img_h) - 5
            cv2.rectangle(img, (rel_box[0], rel_box[1]), (rel_box[2], rel_box[3]), (0,255,0), 2)
            cv2.putText(img, label, (text_offset_x, text_offset_y), font, font_scale, (255, 255, 255), font_thickness, lineType=cv2.LINE_AA)
            if yolo_result.boxes.cls[i] == 0 and depth <140:
                red_light = True
            if yolo_result.boxes.cls[i] == 3 and depth <140:
                red_light = True
    # cv2.imwrite("image.jpg", img)
    # cv2.imwrite("depth.jpg", depth_color)
    # cv2.imwrite('bired_view',bird_view)
    return img, depth_color, bird_view, red_light, pedestrian
    
if __name__ == "__main__":
    multiprocessing.set_start_method('spawn')
    main()