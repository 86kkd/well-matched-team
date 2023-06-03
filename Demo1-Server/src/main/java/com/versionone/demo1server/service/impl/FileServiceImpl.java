package com.versionone.demo1server.service.impl;

import com.versionone.demo1server.object.dto.File;
import com.versionone.demo1server.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public void saveVideoTo_RAM(MultipartFile video) throws IOException{
        File.nowVideoFile = video.getBytes();
        File.nowVideoFilename = video.getOriginalFilename();
    }

    /**
     * 输出视频二进制流到前台
     * @param response 响应体对象
     * @return 成功与否
     * @throws IOException IO异常
     */
    @Override
    public byte[] outputVideo(){
/*//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(File.nowVideoFilename, "UTF-8"));
        response.setContentType("application/octet-stream");
        MediaType.APPLICATION_OCTET_STREAM
        // 告知浏览器文件的大小
//        response.addHeader("Content-Length", "" + File.nowVideoFile.length);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = File.nowVideoFile;
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();*/
        return File.nowVideoFile;
    }

    @Override
    public void saveImageTo_RAM(MultipartFile png) throws IOException {
        File.pendingProcessingImg = png.getBytes();

    }
}
