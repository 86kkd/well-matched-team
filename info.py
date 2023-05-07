from visionUtils.videoDemo import detectModel
from visionUtils.videoDemo import vinoModel
import base64
import sys

## 用openvino加速
model_path = 'visionUtils/data/onnx/yolop-384-640.onnx'
detector = vinoModel(model_path)  

## 更精确的模型但是没有加速
# model_path='visionUtils/data/weights/yolopv2.pt'
# detector = detectModel(model_path)

encoded_image_data = sys.argv[1]

# 对字节数据进行解码
img = base64.b64decode(encoded_image_data)

# 用模型处理图片
det_img = detector(img)  # return detect result 

# 使用base64.b64encode()方法进行编码
encoded_image_data = base64.b64encode(det_img)
# 将编码后的数据转换为字符串形式
encoded_image_str = encoded_image_data.decode('utf-8')
print(encoded_image_str)


