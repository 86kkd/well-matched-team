package com.versionone.demo1server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class SpringMVCConfig {
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        // 设置上传文件内容存储在内存中的阈值（单位为字节）
        resolver.setMaxInMemorySize(1024 * 1024 * 512); // 512 MB
        return resolver;
    }
}
