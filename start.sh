conda activate uniad
python "demo copy.py" --max_depth 80.0 --max_depth_eval 80.0 --backbone swin_large_v2 --depths 2 2 18 2 \
    --num_filters 32 32 32 --deconv_kernels 2 2 2 --window_size 22 22 22 11 --pretrain_window_size 12 12 12 6 \
    --use_shift True True False False --shift_size 2 --ckpt_dir ckpt/kitti_swin_large.ckpt

nohup java -jar Server.jar &

nginx -c /etc/nginx/nginx.conf