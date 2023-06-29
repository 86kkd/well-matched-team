package com.versionone.demo1server.controller;

import com.versionone.demo1server.handler.VideoHttpRequestHandler;
import com.versionone.demo1server.service.FileService;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * 文件事务层对象
     */
    @Autowired
    private FileService fileService;

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

    @RequestMapping(value = {"/video1","/video2","/video3"} , method = RequestMethod.GET)
    public void playVideo(HttpServletRequest request, HttpServletResponse response){
        try {
            videoHttpRequestHandler.handleRequest(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
