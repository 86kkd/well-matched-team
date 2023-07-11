package com.versionone.demo1server.controller;

import com.versionone.demo1server.handler.VideoHttpRequestHandler;
import com.versionone.demo1server.service.FileService;
import com.versionone.demo1server.service.ImageService;
import com.versionone.demo1server.statics.Redis;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件相关接口
 */
@Controller
public class FileController {

    @Autowired
    private VideoHttpRequestHandler videoHttpRequestHandler;


    /**
     * 负责文件业务的事务对象
     */
    @Autowired
    private FileService fileService;

    /**
     * 负责图片业务的事务对象
     */
    @Autowired
    private ImageService imageService;

    /**
     * 测试图片队列和视频拆分图片的接口
     * @param power url参数
     * @return 结果对象
     */
    @RequestMapping(value = "/start" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> start(@RequestParam("power") String power){
        if (power.equals("114514")){
            imageService.videoToImages();                     //视频拆分图片
            return CommonResult.success("114514");            //成功地返回值
        }
        return CommonResult.success("1");                     //参数不正确的返回
    }


    @RequestMapping(value = "/uploadPng" , method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> pngUpload(@RequestPart("png")MultipartFile png){
        if (png.isEmpty()) {
            return CommonResult.failed("上传文件为空");
        }
        try {
            fileService.saveImageTo_RAM(png);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed("文件上传失败");
        }
        return CommonResult.success("文件上传成功");
    }

    /**
     * 上传视频接口
     * @param video 视频二进制流
     * @return 相关信息
     */
    @RequestMapping(value = "/uploadVideo" , method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> videoFileUp(@RequestPart("file")MultipartFile  video){
        if (video.isEmpty()) {
            return CommonResult.failed("上传文件为空");
        }
        try {
            fileService.saveVideoTo_RAM(video);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed("文件上传失败");
        }
        return CommonResult.success("文件上传成功");
    }

    /**
     * 下载视频接口
     * @param response 响应体对象
     * @return 结果
     */
    @RequestMapping(value = "/downloadVideo" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> videoFileDownload(HttpServletResponse response){
        try {
            return fileService.outputVideo(response) ? CommonResult.success("下载成功") : CommonResult.failed();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed();
        }
    }

    /**
     * 视频接口
     * @param request 请求对象
     * @param response 响应对象
     */
    @RequestMapping(value = {"/video1"} , method = RequestMethod.GET)
    public void playVideo1(HttpServletRequest request, HttpServletResponse response){
        try {
            Redis.FILE_NAME = "bired.mp4";
            videoHttpRequestHandler.handleRequest(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/video2"} , method = RequestMethod.GET)
    public void playVideo2(HttpServletRequest request, HttpServletResponse response){
        try {
            Redis.FILE_NAME = "output.mp4";
            videoHttpRequestHandler.handleRequest(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/video3"} , method = RequestMethod.GET)
    public void playVideo3(HttpServletRequest request, HttpServletResponse response){
        try {
            Redis.FILE_NAME = "depth.mp4";
            videoHttpRequestHandler.handleRequest(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
