package com.versionone.demo1server.config;

import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Mapper初始化类，应用启动时将mapper赋值到Redis的静态变量中
 */
@Component
public class MapperInitializer implements ApplicationRunner {

    /**
     * CarMapper对象
     */
    @Autowired
    private CarMapper carMapper;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Redis.carMapper = carMapper;
    }
}
