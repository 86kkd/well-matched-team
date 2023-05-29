package com.versionone.demo1server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.versionone.demo1server.object.entity.Car;

public interface CarService extends IService<Car> {

    Car getCarInfoById(Integer id);

}
