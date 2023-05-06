package com.versionone.demo1server.controller;

import com.versionone.demo1server.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 图片相关接口
 */
@Controller
public class ImageController {

    /**
     * 获取随机图片接口
     */
    @RequestMapping("/getLatestImage-test")
    public void imageDown(HttpServletResponse response){

        try {
            ImageService.getRandomImage(response);
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
}
