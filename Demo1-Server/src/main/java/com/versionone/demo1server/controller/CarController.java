package com.versionone.demo1server.controller;

import com.versionone.demo1server.object.entity.Car;
import com.versionone.demo1server.service.CarService;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class CarController {

    @Autowired
    private CarService service;

    /**
     * 获取随机车辆信息接口
     * @return 车辆信息对象
     */
    @RequestMapping(value = "/getRandomCarInfo" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Car> getRandomCarInfo(){
        try {
            Random r = new Random();
            return CommonResult.success(service.getCarInfoById(r.nextInt(1000) + 1));
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.failed();
        }
    }

}
