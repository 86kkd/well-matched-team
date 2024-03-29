package com.versionone.demo1server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.versionone.demo1server.object.entity.Car;

/**
 * 汽车事务接口
 */
public interface CarService extends IService<Car> {

    Car getCarInfoById(Integer id);

    Car getRandomCarInfo();

    Car brake(Integer id, Double time ,Double strength);

    Car accelerate(Integer id, Double time ,Double strength);

    Car shiftSwitching(Integer id, Integer gear);

    Car shiftLight(Integer id, Integer light);

    Car shiftTurnLight(Integer id, Integer turnLight);

    Car shiftDoor(Integer id, Integer door);

    Car shiftBreak(Integer id);
}
