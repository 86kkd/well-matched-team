from visionUtils.videoDemo import detectModel
from visionUtils.videoDemo import vinoModel
## 用openvino加速
model_path = 'visionUtils/data/onnx/yolop-384-640.onnx'
detector = vinoModel(model_path)  

## 更精确的模型但是没有加速
# model_path='visionUtils/data/weights/yolopv2.pt'
# detector = detectModel(model_path)

# 读图片
from PIL import Image
import numpy as np
image_path = 'example.jpg'
img_pil = Image.open(image_path)
img = np.array(img_pil)

# 用模型处理图片
det_img = detector(img)  # return detect result 

# 展示结果
img_pil = Image.fromarray(np.uint8(det_img))
img_pil.show()