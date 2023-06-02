package com.versionone.demo1server.statics;

import com.versionone.demo1server.object.entity.Car;

import java.util.LinkedList;
import java.util.List;

import static com.versionone.demo1server.statics.StaticObject.RANDOM;

/**
 * 简易的内存数据库
 */
public class Redis {

    static {
        cars = new LinkedList<>();
    }

    private static  List<Car> cars;


    /**
     * 获取随机缓存汽车数据
     * @return 汽车对象
     */
    public static Car getRandomCar(){
        int i = RANDOM.nextInt(cars.size());
        return cars.get(i);
    }

    /**
     * 设置汽车信息表
     * @param cars_ 汽车信息表
     */
    public static void setCars(List<Car> cars_){
        cars = cars_;
    }
}
