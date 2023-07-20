FROM jklinda/well-matched:latest
# LABEL Name=well-matched Version=latest

# EXPOSE 80

COPY nginx.conf /etc/nginx/nginx.conf
COPY . /app


# RUN sed -i 's/archive.ubuntu.com/mirrors.huaweicloud.com/g' /etc/apt/sources.list && \
#     apt-get clean && \
#     apt-get update && \
#     apt-get upgrade -y

# # 3 install dev-tools
# RUN apt-get install -y  \
#     build-essential \
#     cmake \
#     git \
#     libglib2.0-0 \
#     ca-certificates \
#     wget \
#     curl \
#     libffi-dev \
#     libssl-dev \
#     zlib1g-dev \
#     libgl1-mesa-glx\
#     python3.11
# RUN dpkg --configure -a && apt-get install -f && apt-get clean && apt-get update && apt-get install -y \
#     nginx\
#     openjdk-11-jdk

# RUN ln -s $(which python3) /usr/bin/python
# RUN curl https://bootstrap.pypa.io/get-pip.py|python 

# # 8 install pytroch
# RUN pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu118 --no-cache-dir

# # 9-11 install packages
# RUN pip install mmcv-full timm albumentations -i https://pypi.tuna.tsinghua.edu.cn/simple --no-cache-d

WORKDIR /app
# RUN /root/anaconda3/bin/conda init bash && \
#     . ~/.bashrc && \
#     conda activate uniad \
    # && python "demo copy.py" --max_depth 80.0 --max_depth_eval 80.0 --backbone swin_large_v2 --depths 2 2 18 2 \
    # --num_filters 32 32 32 --deconv_kernels 2 2 2 --window_size 22 22 22 11 --pretrain_window_size 12 12 12 6 \
    # --use_shift True True False False --shift_size 2 --ckpt_dir ckpt/kitti_swin_large.ckpt

# RUN nohup java -jar Server.jar &
# CMD ["/bin/bash"]
# CMD ["/bin/bash","start.sh"]


