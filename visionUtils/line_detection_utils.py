import cv2 
import numpy as np 
import pickle
import matplotlib.pyplot as plt
from visionUtils.utils.utils import xyxy2xywh
import torch
import math

# fullsize:1280x720
x = [194, 1117, 705, 575]
y = [719, 719, 461, 461]

# Only for creating the final video visualization
X_b = [574, 706, 706, 574]
Y_b = [719, 719, 0, 0]
src_ = np.floor(np.float32([[x[0], y[0]], [x[1], y[1]],[x[2], y[2]], [x[3], y[3]]]) ) 
dst_ = np.floor(np.float32([[X_b[0], Y_b[0]], [X_b[1], Y_b[1]],[X_b[2], Y_b[2]], [X_b[3], Y_b[3]]])) 
M_b = cv2.getPerspectiveTransform(src_, dst_)



def warper(img, M):

    # Compute and apply perspective transform
    img_size = (img.shape[1], img.shape[0])
    warped = cv2.warpPerspective(img, M, img_size, flags=cv2.INTER_NEAREST)  # keep same size as input image

    return warped

def fit_dual_lane(left_lane, right_lane, lane_mask, step_pix=10,window_width=15):
   
    # squeeze the lane conters
    left_lane = left_lane.squeeze()
    right_lane = right_lane.squeeze()
    # fit the conters with line 
    right_fit = np.polyfit(left_lane[:,1],left_lane[:,0],2)
    left_fit = np.polyfit(right_lane[:,1],right_lane[:,0],2)
    # initial points to be used in lane
    lane_points = np.argwhere(lane_mask == 1) #y,x
    right_points = np.empty((0, 2))  # num_columns是右侧点的列数
    left_points = np.empty((0, 2))   # num_columns是左侧点的列数

    for cut_line in range(lane_mask.shape[0]-1,0,-step_pix):
        # get detect center
        right_center = right_fit[0]*cut_line**2 + right_fit[1]*cut_line + right_fit[2]
        left_center = left_fit[0]*cut_line**2 + left_fit[1]*cut_line +  left_fit[2]
        # find point with in the window
        left_point = lane_points[(lane_points[:, 1] >= left_center - window_width) \
            & (lane_points[:, 1] < left_center + window_width) \
            & (lane_points[:, 0] <= cut_line) \
            & (lane_points[:, 0] > cut_line - step_pix), :]
        right_point = lane_points[(lane_points[:, 1] >= right_center - window_width) \
            & (lane_points[:, 1] < right_center + window_width) \
            & (lane_points[:, 0] <= cut_line) \
            & (lane_points[:, 0] > cut_line - step_pix), :]
        
        if len(left_point) != 0  or len(right_point) != 0:
            right_points = np.concatenate((right_points,right_point),axis=0)
            left_points = np.concatenate((left_points,left_point),axis=0)
        else:
            return right_fit, left_fit, cut_line   
        if cut_line < lane_mask.shape[0]*3/4:
            # fit y,x
            right_fit = np.polyfit(right_points[:,0],right_points[:,1],2)
            left_fit = np.polyfit(left_points[:,0],left_points[:,1],2)
    
# 遍历轮廓，找到最接近中心和次接近中心的轮廓
def get_dual_lane(contours,image_center):
    min_distance = float('inf')
    second_min_distance = float('inf')
    closest_contour_index = -1
    second_closest_index = -1
    # 计算轮廓和中心的距离
    for i, contour in enumerate(contours):
        x, _, w, _ = cv2.boundingRect(contour)
        contour_center_x = x + (w // 2)
        distance = abs(contour_center_x - image_center)

        # 离中心点的距离最小和次小的轮廓
        if distance < min_distance:
            second_min_distance = min_distance
            second_closest_index = closest_contour_index
            min_distance = distance
            closest_contour_index = i
        elif distance < second_min_distance and i != closest_contour_index:
            second_min_distance = distance
            second_closest_index = i

    # 从左到右排序
    if closest_contour_index != -1 and second_closest_index != -1:      
        closest_contours = [contours[closest_contour_index], contours[second_closest_index]]
        closest_contours = sorted(closest_contours, key=lambda c: cv2.boundingRect(c)[0])
        
        return closest_contours
    else:
        return None


# 对每个轮廓进行多项式拟合,返回二次多项式系数
def poly_fit_lane(contours):
    coefficients = []
    
    for contour in contours:
        contour_points = contour[:, 0, :]  # 提取轮廓中的坐标点
        x = contour_points[:, 0]
        y = contour_points[:, 1]
        coefficients.append(np.polyfit(x, y, deg=2))  # 使用二次多项式进行拟合
        
    return coefficients
def visualization(img):
    ploty = np.linspace(0, img.shape[0]-1, img.shape[0])   

# 找到一个轮廓中最低的点
def buttom_x(parameter,img_height):
            
    return parameter[0]*img_height**2 \
            + parameter[1]*img_height \
            + parameter[2]
def visual(mask,x1,x2,y):
    plt.clf()
    plt.imshow(mask,cmap='gray')
    plt.plot(x1,y)
    plt.plot(x2,y)
    plt.draw()
    plt.pause(0.0001)
def visual_fit(mask,fit1,fit2,lane_cut):
    height = mask.shape[0]
    plt.clf()
    plt.imshow(mask),
    plot = np.linspace(lane_cut,height,height-lane_cut)
    line1 = fit1[0]*plot**2 \
                        + fit1[1]*plot \
                        + fit1[2]
    line2 = fit2[0]*plot**2 \
                        + fit2[1]*plot \
                        + fit2[2]
    plt.plot(line1,plot)
    plt.plot(line2,plot)
    plt.draw()
    plt.pause(0.0001)

offset_window = [0.]
offset_window_size = 1
offset_threshood = 30
# 车道线检测方法二，使用轮廓检测加滑动窗口
def evaluate_offset(lane_mask, bias=30, cut_rate=4/5, visualize=False):
    
    # get image width and height
    width = lane_mask.shape[1] 
    height = lane_mask.shape[0]
    
    # first cut the image and detect the buttom line
    cut_line = int(lane_mask.shape[0]*cut_rate)
    cut_mask = lane_mask[cut_line:,:].astype(np.uint8)*255
    contours, hierarchy = cv2.findContours(cut_mask,cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    for contour in contours:
        contour[:,0,1] += cut_line
    contour_img = np.ones_like(lane_mask)
    # cv2.drawContours(contour_img,contours,-1,(0,255,0),2)
    # plt.imshow(contour_img)
    # plt.pause(0.000001)
    # celect center lane line 
    
    image_center = width//2
    left_lane, right_lane = get_dual_lane(contours,image_center)
    right_fit, left_fit, lane_cut = fit_dual_lane(left_lane,right_lane,lane_mask) 
    # visual_fit(lane_mask,right_fit,left_fit,height-height//6)
    
    # 找最低点
    left_x = buttom_x(left_fit,img_height=height-height//6)
    right_x = buttom_x(right_fit,img_height=height-height//6)
    
    # 定义车量偏移
    drive_center = width//2 + bias
    offset = math.log(abs(drive_center-left_x)/abs(drive_center-right_x))
    return offset,right_fit,left_fit,cut_line

# 估计车辆距离
def evaluate_distance(det,gn,img:np.ndarray):
    focal = 7000 # 相机焦距
    target = 2 # 车辆现实平均距离
    cls_distance = []
    for *xyxy, conf, cls in reversed(det):
        xywh = (xyxy2xywh(torch.tensor(xyxy).view(1, 4)) / gn).view(-1).tolist()  # normalized xywh
        _, _, width, _ = xywh*img.shape[1]
        distance = focal * target / width
        cls_distance.append([cls, distance])
    return cls_distance
        
        
        