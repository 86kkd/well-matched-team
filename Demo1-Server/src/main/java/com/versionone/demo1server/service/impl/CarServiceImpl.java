package com.versionone.demo1server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.service.CarService;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Autowired
    private CarMapper mapper;

    @Override
    public Car getCarInfoById(Integer id) {
       return getById(id);
    }

    @Override
    public Car getRandomCarInfo() {
        return Redis.getRandomCar();
    }

}
