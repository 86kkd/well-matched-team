package com.versionone.demo1server.threads;

import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.statics.Redis;

import java.util.Random;

public class VSOARandomDataThread extends java.lang.Thread{
    private final Random random = Redis.RANDOM;
    private final VSOACarService carService;

    public VSOARandomDataThread(VSOACarService service){
        this.carService = service;
    }

    @Override
    public void run() {
        while (true){
            int op = random.nextInt(5) + 1;
            switch (op){
                case 1 : carService.setMileage(getRandom()); break;
                case 2 : carService.setPower(getRandomPower()); break;
                case 3 : carService.setSpeed(getRandomSpeed()); break;
                case 4 : carService.setGear(getRandomGear()); break;
                case 5 : carService.setLight(getRandomLight()); break;
            }
            try {
                java.lang.Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getRandom(){
        return random.nextInt(100000);
    }

    public int getRandomPower(){
        return random.nextInt(100);
    }

    public int getRandomSpeed(){
        return random.nextInt(240);
    }

    public int getRandomGear(){
        return random.nextInt(6);
    }

    public int getRandomLight(){
        int[] data = {0,1,2,4,8};
        return data[random.nextInt(5)];
    }
}
