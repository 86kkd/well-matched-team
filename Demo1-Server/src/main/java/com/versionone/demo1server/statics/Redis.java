package com.versionone.demo1server.statics;

import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;

import java.util.LinkedList;
import java.util.List;

import static com.versionone.demo1server.statics.StaticObject.RANDOM;

/**
 * 简易的内存数据库
 */
public class Redis {

    /*
     *汽车信息表初始化
     */
    static {
        cars = new LinkedList<>();
    }

    private static  List<Car> cars;

    /**
     * 汽车信息表数据库读写接口对象
     */
    public static CarMapper carMapper;

    /**
     * 更新汽车信息方法
     * @param id 汽车id
     * @param car 汽车对象
     */
    public static void updateCarInfoById(Integer id,Car car){
        cars.set(id,car);
    }

    /**
     * 获取随机缓存汽车数据
     * @return 汽车对象
     */
    public static Car getRandomCar(){
        int i = RANDOM.nextInt(cars.size());
        return cars.get(i);
    }

    /**
     * 通过id获取汽车对象
     * @return 汽车对象
     */
    public static Car getCarById(Integer id){
        return cars.get(id - 1);
    }

    /**
     * 设置汽车信息表
     * @param cars_ 汽车信息表
     */
    public static void setCars(List<Car> cars_){
        cars = cars_;
    }
}
