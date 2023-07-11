package com.versionone.demo1server.handler;

import com.versionone.demo1server.statics.Redis;
import com.versionone.demo1server.utils.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 视频处理类
 */
@Component
public class VideoHttpRequestHandler extends ResourceHttpRequestHandler {

    @Override
    protected Resource getResource(HttpServletRequest request) {
        return new FileSystemResource(new File("./" + Redis.FILE_NAME));
    }
}
