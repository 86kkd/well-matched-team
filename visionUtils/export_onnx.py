import torch

model_path = 'visionUtils/data/weights/yolopv2.pt'
IMAGE_HEIGHT, IMAGE_WIDTH = 384, 640
onnx_path = 'visionUtils/data/onnx/yolopv2.onnx'

model = torch.jit.load(model_path)
model.to('cpu')  # 如果不转到cpu会报错
dummy_input = torch.randn(1, 3, IMAGE_HEIGHT, IMAGE_WIDTH)
torch.onnx.export(
    model,
    dummy_input,
    onnx_path,
    output_names=['det_out', 'drive_area_seg', 'lane_line_seg']
    # opset_version=11
)
print(f"ONNX model exported to {onnx_path}.")