# 使用官方的Anaconda3基础镜像
FROM continuumio/anaconda3:latest

# 设置工作目录
WORKDIR /app

# 更新系统并安装依赖
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    build-essential \
    libgl1 libglib2.0-0 libsm6 libxext6 libxrender1 libfontconfig1 \
    cmake \
    git \ 
    && rm -rf /var/lib/apt/lists/*

# 安装python依赖
RUN conda install -y  pytorch torchvision  pytorch-cuda=11.8 -c pytorch -c nvidia
RUN pip install openvino opencv-python -i https://pypi.tuna.tsinghua.edu.cn/simple some-package
RUN conda clean --all && \
    rm -rf /root/.cache/pip/*
## 先看看用python的包可不可以
# # 安装OpenVINO
# RUN wget https://apt.repos.intel.com/openvino/2021/GPG-PUB-KEY-INTEL-OPENVINO-2021 && \
#     apt-key add GPG-PUB-KEY-INTEL-OPENVINO-2021 && \
#     echo "deb https://apt.repos.intel.com/openvino/2021 all main" | tee /etc/apt/sources.list.d/intel-openvino-2021.list && \
#     apt-get update && \
#     apt-get install -y intel-openvino-dev-ubuntu18-2021.3.394 && \
#     rm -rf /var/lib/apt/lists/*

# # 设置OpenVINO环境变量
# ENV INTEL_OPENVINO_DIR /opt/intel/openvino_2021
# ENV LD_LIBRARY_PATH $INTEL_OPENVINO_DIR/deployment_tools/inference_engine/external/tbb/lib:$LD_LIBRARY_PATH
# ENV PYTHONPATH $INTEL_OPENVINO_DIR/python/python3.7:$INTEL_OPENVINO_DIR/python/python3:$PYTHONPATH

# 安装TensorRT
# RUN apt-get update && \
#     apt-get install -y --no-install-recommends \
#     gnupg2 \
#     curl \
#     ca-certificates \
#     && echo "deb https://developer.download.nvidia.com/compute/machine-learning/repos/ubuntu1804/x86_64 /" > /etc/apt/sources.list.d/nvidia-ml.list \
#     && apt-key adv --fetch-keys https://developer.download.nvidia.com/compute/cuda/repos/ubuntu1804/x86_64/7fa2af80.pub \
#     && apt-get update \
#     && apt-get install -y --no-install-recommends \
#     libnvinfer7=7.2.1-1+cuda11.1 \
#     libnvinfer-plugin7=7.2.1-1+cuda11.1 \
#     libnvinfer-dev=7.2.1-1+cuda11.1 \
#     libnvinfer-plugin-dev=7.2.1-1+cuda11.1 \
#     libnvonnxparsers7=7.2.1-1+cuda11.1 \
#     libnvonnxparsers-dev=7.2.1-1+cuda11.1 \
#     libnvparsers7=7.2.1-1+cuda11.1 \
#     libnvparsers-dev=7.2.1-1+cuda11.1 \
#     libnvidia-ml1=450.119.04-0ubuntu1 \
#     && rm -rf /var/lib/apt/lists/*

# # 设置TensorRT环境变量
# ENV LD_LIBRARY_PATH /usr/lib/x86_64-linux-gnu:$LD_LIBRARY_PATH

# 用于存放模型和数据的目录
RUN mkdir -p /well-matched/data
RUN mkdir -p /well-matched/models

# 复制代码到工作目录
COPY . /well-matched

# # 安装Python依赖库
# RUN pip install --no-cache-dir -r requirements.txt

# 设置默认命令
# CMD ["python", "your_main_script.py"]

