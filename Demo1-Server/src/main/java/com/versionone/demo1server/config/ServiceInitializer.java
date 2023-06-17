package com.versionone.demo1server.config;

import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ServiceInitializer implements ApplicationRunner {

    @Autowired
    private VSOACarService vsoaCarService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Redis.vsoaCarService = vsoaCarService;
    }
}
