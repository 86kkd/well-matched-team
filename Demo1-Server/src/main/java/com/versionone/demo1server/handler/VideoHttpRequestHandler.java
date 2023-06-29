package com.versionone.demo1server.handler;

import com.versionone.demo1server.object.dto.File;
import com.versionone.demo1server.utils.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频处理类
 */
@Component
public class VideoHttpRequestHandler extends ResourceHttpRequestHandler {

    @Override
    protected Resource getResource(HttpServletRequest request) {
        return new FileSystemResource(FileUtil.byteToFile(File.nowVideoFile,"./",File.nowVideoFilename));
    }
}
