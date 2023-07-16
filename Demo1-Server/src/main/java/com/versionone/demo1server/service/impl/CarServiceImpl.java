package com.versionone.demo1server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.service.CarService;
import com.versionone.demo1server.statics.Redis;
import com.versionone.demo1server.utils.RuleMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.versionone.demo1server.statics.Redis.RANDOM;

/**
 * 汽车对象事务
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    /**
     * 汽车数据库表的读写接口对象
     */
    @Autowired
    private CarMapper mapper;

    /**
     * 通过id获取汽车对象
     * @param id 汽车id
     * @return 汽车对象
     */
    @Override
    public Car getCarInfoById(Integer id) {
        if (id <= 0){
            return null;
        }
        return getById(id);
    }

    /**
     * 获取随机汽车对象信息
     * @return 汽车对象
     */
    @Override
    public Car getRandomCarInfo() {
        return Redis.getRandomCar();
    }

    /**
     * 刹车业务
     * @param id 汽车id
     * @param time 刹车时间
     * @param strength 刹车力度
     * @return 汽车信息
     */
    @Override
    public Car brake(Integer id, Double time, Double strength) {
        if (strength<0 || strength>10 || id<=0){
            return null;
        }
        Car car = getCarById(id);
        Double speed = car.getSpeed();
        Double resultSpeed = getBrakeResult(time,speed,strength);
        car.setSpeed(resultSpeed);
//        saveOrUpdate(car);
//        updateCarOnList(car,id);
        return subsequentOperations(car,id);
    }

    /**
     * 汽车加速业务
     * @param id 汽车id
     * @param time 加速时间
     * @param strength 加速油门力度
     * @return 汽车对象
     */
    @Override
    public Car accelerate(Integer id, Double time, Double strength) {
        if (strength<0 || strength>10 || id<=0){
            return null;
        }
        Car car = getCarById(id);
        Integer gear = car.getGear();
        Integer braking = car.getBraking();
        Integer door = car.getDoor();
        if (gear == 0 || gear == 2 || braking == 1 || door != 0){       //N档或者P档，正在拉着手刹，车门没关完，不允许加速
            return car;
        }
        Double speed = car.getSpeed();
        Double resultSpeed = getAccelerateResult(time,speed,strength);
        car.setSpeed(resultSpeed);
//        saveOrUpdate(car);
//        updateCarOnList(car,id);
        return subsequentOperations(car,id);
    }


    /**
     * 切换挡位
     * @param id 汽车id
     * @param gear 挡位
     * @return 汽车对象
     */
    @Override
    public Car shiftSwitching(Integer id, Integer gear) {
        if (RuleMatch.gearMatching(gear)){ //挡位不合法
            return null;
        }
        Car car = getCarById(id);
        car.setGear(gear);
//        saveOrUpdate(car);
//        updateCarOnList(car,id);
        return subsequentOperations(car,id);
    }

    /**
     * 切换灯光
     * @param id 汽车id
     * @param light 灯光值
     * @return 汽车对象
     */
    @Override
    public Car shiftLight(Integer id, Integer light) {
        if (RuleMatch.lightMatching(light)){
            return null;
        }
        Car car = getCarById(id);
        car.setLight(light);
        return subsequentOperations(car,id);
    }

    /**
     * 切换转向灯
     * @param id 汽车id
     * @param turnLight 转向灯值
     * @return 汽车对象
     */
    @Override
    public Car shiftTurnLight(Integer id, Integer turnLight) {
        if (RuleMatch.turnLightMatching(turnLight)){
            return null;
        }
        Car car = getCarById(id);
        car.setTurnLight(turnLight);
        return subsequentOperations(car,id);
    }

    /**
     * 切换车门状态
     * @param id 汽车id
     * @param door 车门状态值
     * @return 汽车对象
     */
    @Override
    public Car shiftDoor(Integer id, Integer door) {
        if (RuleMatch.doorMatching(door)){
            return null;
        }
        Car car = getCarById(id);
        car.setDoor(door);
        return subsequentOperations(car,id);
    }

    @Override
    public Car shiftBreak(Integer id) {
        Car car = getCarById(id);
        car.setBraking( car.getBraking() == 1 ? 0 : 1 );
        return subsequentOperations(car,id);
    }

    /**
     * 后置处理方法，用于更新车辆信息
     * @param car 汽车对象
     * @param id 汽车id
     * @return 汽车对象
     */
    private Car subsequentOperations(Car car,Integer id){
        saveOrUpdate(car);
        updateCarOnList(car, id);
        return car;
    }

    /**
     * 汽车加速函数
     * 普通汽车加速的最大加速度在50-150米每秒²
     * 用油门的力度来控制刹车的加速度
     * 用了简单的匀速直线运动速度变化公式计算汽车的终速度
     * @param time 时间，单位毫秒
     * @param speed 速度，单位km/h
     * @param strength 力度 范围[0-10]
     * @return 加速终速度
     */
    private Double getAccelerateResult(Double time,Double speed,Double strength){
        double acceleration = ( 0.5 + RANDOM.nextDouble() )*strength;
        double resultSpeed  = speed + acceleration*(time/1000)*3.6;             // 1m/s = 3.6km/h
        if (resultSpeed > 240){
            return 240.0;
        }
        return resultSpeed;
    }

    /**
     * 更新汽车信息列表
     * @param car 汽车对象
     * @param id 汽车id
     */
    private void updateCarOnList(Car car,Integer id){
        Redis.updateCarInfoById(id, car);
    }

    /**
     * 汽车刹车函数
     * 普通汽车刹车的最大加速度在60-80米每秒²
     * 用刹车的力度来控制刹车的加速度
     * 用了简单的匀速直线运动速度变化公式计算汽车的终速度
     * @param time 时间，单位毫秒
     * @param speed 速度，单位km/h
     * @param strength 力度 范围[0-10]
     * @return 刹车终速度
     */
    private Double getBrakeResult(Double time,Double speed,Double strength){
        double acceleration = ( 0.6 + (0.8-0.6) * RANDOM.nextDouble() )*strength;
        double resultSpeed  = speed - acceleration*(time/1000)*3.6;             // 1m/s = 3.6km/h
        if (resultSpeed < 0){
            return 0.0;
        }
        return resultSpeed;
    }

    /**
     * 通过id获取汽车对象
     * @param id 汽车id
     * @return 汽车对象
     */
    private Car getCarById(Integer id){
        return Redis.getCarById(id);
    }
}
