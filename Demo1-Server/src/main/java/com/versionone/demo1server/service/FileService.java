package com.versionone.demo1server.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileService {

    void saveVideoTo_RAM(MultipartFile video) throws IOException;

    byte[] outputVideo();

    void saveImageTo_RAM(MultipartFile png) throws IOException;
}
