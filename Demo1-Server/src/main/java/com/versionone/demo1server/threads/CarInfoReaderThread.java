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

    private static class PrivateCarInfoReaderThread extends java.lang.Thread{

        private final CarMapper carMapper;

        public PrivateCarInfoReaderThread(CarMapper carMapper){
            this.carMapper = carMapper;
        }

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
