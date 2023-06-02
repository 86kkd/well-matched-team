package com.versionone.demo1server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.service.CarService;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.versionone.demo1server.statics.StaticObject.RANDOM;

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

    @Override
    public Car brake(Integer id, Double time, Double strength) {
        if (strength<0 || strength>10 || id<=0){
            return null;
        }
        Car car = getCarById(id);
        Double speed = car.getSpeed();
        Double resultSpeed = getBrakeResult(time,speed,strength);
        car.setSpeed(resultSpeed);
        saveOrUpdate(car);
        updateCarOnList(car,id);
        return car;
    }

    private void updateCarOnList(Car car,Integer id){
        Redis.updateCarInfoById(id, car);
    }

    private Double getBrakeResult(Double time,Double speed,Double strength){
        double acceleration = ( 0.6 + (0.8-0.6) * RANDOM.nextDouble() )*strength ;
        double resultSpeed  = speed - acceleration*(time/1000);
        if (resultSpeed < 0){
            return 0.0;
        }
        return resultSpeed;
    }

    private Car getCarById(Integer id){
        return Redis.getCarById(id);
    }
}
