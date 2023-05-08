from visionUtils.videoDemo import vinoModel
import base64

from PIL import Image, ImageFile
import numpy as np
from io import BytesIO
ImageFile.LOAD_TRUNCATED_IMAGES = True

def decoder(image_data):
    # 对字节数据进行解码
    decoded_image_data = base64.b64decode(image_data)
    image = Image.open(BytesIO(decoded_image_data))
    img_np = np.array(image)
    # image.show()
    return img_np
def encoder(det_img):
    image = Image.fromarray(det_img)
    # 将PIL Image对象转换为字节数据
    buffer = BytesIO()
    image.save(buffer, format='JPEG')
    image_data = buffer.getvalue()
    # 将字节数据编码为base64字符串
    base64_image_data = base64.b64encode(image_data).decode('utf-8')

    return base64_image_data
    
def fun(data) :
    ## 用openvino加速
    model_path = 'visionUtils/data/onnx/yolop-384-640.onnx'
    detector = vinoModel(model_path)

    ## 更精确的模型但是没有加速
    # model_path='visionUtils/data/weights/yolopv2.pt'
    # detector = detectModel(model_path)

    # base64转图片
    image_data = data
    img = decoder(image_data)

    # 用模型处理图片
    det_img = detector(img)  # return detect result

    # 图片转base64
    base64_img = encoder(det_img)

    return base64_img



