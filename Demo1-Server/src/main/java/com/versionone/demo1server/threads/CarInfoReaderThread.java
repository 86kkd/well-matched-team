package com.versionone.demo1server.threads;

import com.versionone.demo1server.mapper.CarMapper;
import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 汽车信息读取线程
 */
@Component
public class CarInfoReaderThread{

    @Autowired
    private CarMapper carMapper;

    static {
        java.lang.Thread thread = new PrivateCarInfoReaderThread();
        thread.start();
    }

    /**
     * 原神，启动！
     */
    public static void start(){

    }

    private static class PrivateCarInfoReaderThread extends java.lang.Thread{

        @Override
        public void run() {
            while (true) {
                List<Car> cars = carMapper.selectList(null);
                System.out.println(cars);
                Redis.setCars(cars);
                try {
                    java.lang.Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
