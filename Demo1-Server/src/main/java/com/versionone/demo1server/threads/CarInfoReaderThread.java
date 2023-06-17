package com.versionone.demo1server.threads;

import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.statics.Redis;

import java.util.List;

/**
 * 汽车信息读取线程
 */
public class CarInfoReaderThread{

    static {
        java.lang.Thread thread = new PrivateCarInfoReaderThread(Redis.carMapper);
        thread.start();
    }

    /**
     * 原神，启动！
     */
    public static void start(){

    }

    /**
     * 私有内部线程类
     */
    private static class PrivateCarInfoReaderThread extends java.lang.Thread{

        private final CarMapper carMapper;

        /**
         * 通过构造器获取汽车数据库读写接口对象
         * @param carMapper mapper对象
         */
        public PrivateCarInfoReaderThread(CarMapper carMapper){
            this.carMapper = carMapper;
        }

        /**
         * 线程执行逻辑：不断读取数据库中的汽车信息，并存到Redis的汽车表里面，循环间隔时间是3s
         */
        @Override
        public void run() {
            while (true) {
                List<Car> cars = carMapper.selectList(null);
//                System.out.println(cars);
                Redis.setCars(cars);
//                System.out.println(Redis.getRandomCar());
                try {
                    java.lang.Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
