package com.versionone.demo1server.handler;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;

public class VideoHttpRequestHandler
extends ResourceHttpRequestHandler {

    public final static String ATTR_FILE = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        final Path filePath = (Path) request.getAttribute(ATTR_FILE);
        return new FileSystemResource(filePath);
    }
}
