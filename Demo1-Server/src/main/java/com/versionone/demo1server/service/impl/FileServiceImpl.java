package com.versionone.demo1server.service.impl;

import com.versionone.demo1server.object.dto.File;
import com.versionone.demo1server.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件事务
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 保存视频到内存中
     * @param video 视频对象
     * @throws IOException IO异常
     */
    @Override
    public void saveVideoTo_RAM(MultipartFile video ,int id) throws IOException{
        if (id == 1){
            File.nowVideoFile = video.getBytes();
            File.nowVideoFilename = video.getOriginalFilename();
        }else if (id == 2){
            File.v1 = video.getBytes();
            File.v1_n = video.getOriginalFilename();
        }else if (id == 3){
            File.v2 = video.getBytes();
            File.v2_n = video.getOriginalFilename();
        }else if (id == 4){
            File.v3 = video.getBytes();
            File.v3_n = video.getOriginalFilename();
        }
    }



    /**
     * 输出视频二进制流到前台
     * @param response 响应体对象
     * @return 成功与否
     * @throws IOException IO异常
     */
    @Override
    public boolean outputVideo(HttpServletResponse response , int id) throws IOException{
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(File.nowVideoFilename, "UTF-8"));
        response.setContentType("video/mp4");
//        MediaType.APPLICATION_OCTET_STREAM
        // 告知浏览器文件的大小
//        response.addHeader("Content-Length", "" + File.nowVideoFile.length);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = null;
        if (id == 1){
            bytes = File.nowVideoFile;
        }else if (id == 2){
            bytes = File.v1;
        }else if (id == 3){
            bytes = File.v2;
        }else if (id == 4){
            bytes = File.v3;
        }

        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    @Override
    public void saveImageTo_RAM(MultipartFile png) throws IOException {
        File.pendingProcessingImg = png.getBytes();

    }
}
