FROM python:3.10.9

# 设置工作目录
WORKDIR /well-matched

# 更新系统并安装依赖
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    build-essential \
    libgl1 libglib2.0-0 libsm6 libxext6 libxrender1 libfontconfig1 \
    cmake \
    git \ 
    && rm -rf /var/lib/apt/lists/*

# 安装python依赖
RUN pip install --no-cache-dir  torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu118
RUN pip install --no-cache-dir  openvino opencv-python -i https://pypi.tuna.tsinghua.edu.cn/simple some-package
RUN rm -rf /root/.cache/pip/*

# 用于存放模型和数据的目录
RUN mkdir -p /well-matched/data
RUN mkdir -p /well-matched/models

# 复制代码到工作目录
COPY . /well-matched