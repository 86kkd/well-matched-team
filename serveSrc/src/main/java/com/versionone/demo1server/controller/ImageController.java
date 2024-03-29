package com.versionone.demo1server.controller;

import com.versionone.StaticString;
import com.versionone.demo1server.service.ImageService;
import com.versionone.demo1server.statics.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 图片相关接口
 */
@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;       //注入图片服务

    /**
     * 获取随机图片接口
     */
    @RequestMapping(value = "/getLatestImage-test" , method = RequestMethod.GET ,produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public void imageDown(HttpServletResponse response){

        try {
            imageService.getNewImage(response);
        } catch (IOException e) {
            e.printStackTrace();
        }



       /* //创建HttpHeaders对象，设置响应头信息
        MultiValueMap<String,String> headers = new HttpHeaders();
        //设置下载方式和下载文件的名称   attachment表示以附件的形式下载
        headers.add("Content-Disposition","attachment;filename=new.png");
        try {
            return new ResponseEntity<>(ImageService.getRandomImageBytes(),headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        }*/

    }

    @RequestMapping(value = "/newImg",method = RequestMethod.GET)
    @ResponseBody
    public String getNewImg(){
        return Redis.STRING_QUEUE.dequeue();
//        return StaticString.__.toString();
    }

}
