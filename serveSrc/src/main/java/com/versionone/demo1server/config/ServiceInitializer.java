package com.versionone.demo1server.config;

import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 事务层初始化类
 */
@Component
public class ServiceInitializer implements ApplicationRunner {

    @Autowired
    private VSOACarService vsoaCarService;

    /**
     * 在启动时将事务对象赋值到Redis中
     * @param args APP运行参数
     * @throws Exception 异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Redis.vsoaCarService = vsoaCarService;
    }
}
