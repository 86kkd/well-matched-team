package com.versionone.demo1server.service.impl;

import com.versionone.demo1server.object.dto.File;
import com.versionone.demo1server.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public void saveVideoTo_RAM(MultipartFile video) throws IOException{
        File.nowVideoFile = video.getBytes();
        File.nowVideoFilename = video.getOriginalFilename();
    }

    @Override
    public boolean outputVideo(HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(File.nowVideoFilename, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 告知浏览器文件的大小
        response.addHeader("Content-Length", "" + File.nowVideoFile.length);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = File.nowVideoFile;
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        return true;
    }
}
