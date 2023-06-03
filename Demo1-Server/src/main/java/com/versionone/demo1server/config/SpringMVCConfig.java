package com.versionone.demo1server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置信息
 */
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    /**
     * 解决跨域问题
     * @param registry CorsRegistry对象
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        // 设置上传文件内容存储在内存中的阈值（单位为字节）
        resolver.setMaxInMemorySize(1024 * 1024 * 512); // 512 MB
        return resolver;
    }
}
