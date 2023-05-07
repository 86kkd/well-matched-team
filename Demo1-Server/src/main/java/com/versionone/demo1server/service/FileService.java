package com.versionone.demo1server.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {

    void saveVideoTo_RAM(MultipartFile video) throws IOException;

    boolean outputVideo(HttpServletResponse response) throws IOException;

}
