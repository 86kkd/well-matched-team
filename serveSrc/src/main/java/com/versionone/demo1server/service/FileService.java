package com.versionone.demo1server.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件事务接口
 */
public interface FileService {

    void saveVideoTo_RAM(MultipartFile video , int id) throws IOException;

    boolean outputVideo(HttpServletResponse response , int id) throws IOException;

    void saveImageTo_RAM(MultipartFile png) throws IOException;
}
