package com.versionone.demo1server.controller;

import com.versionone.demo1server.service.VSOACarService;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VSOACarController {

    @Autowired
    private VSOACarService service;

    @RequestMapping(value = "/v_setSpeed" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> setSpeed(@RequestParam("value")int speed){
        try {
            return CommonResult.success(service.setSpeed(speed));
        } catch (Exception e) {
            return CommonResult.failed("未知错误");
        }
    }
}
