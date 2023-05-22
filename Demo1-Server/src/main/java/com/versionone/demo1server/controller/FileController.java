package com.versionone.demo1server.controller;

import com.versionone.demo1server.service.FileService;
import com.versionone.demo1server.service.ImageService;
import com.versionone.demo1server.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Arrays;

/**
 * 文件相关接口
 */
@Controller
public class FileController {

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


    @RequestMapping(value = "/downloadVideo" , method = RequestMethod.GET ,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> videoFileDownload(){
        /*try {
            return fileService.outputVideo() ? CommonResult.success("下载成功") : CommonResult.failed();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed();
        }*/

        byte[] videoData = fileService.outputVideo();

        StreamingResponseBody responseBody = outputStream -> {
            // 模拟分块加载，每次写入部分视频数据
            for (int i = 0; i < videoData.length; i += 1024) {
                int chunkSize = Math.min(1024, videoData.length - i);
                byte[] chunkData = Arrays.copyOfRange(videoData, i, i + chunkSize);
                outputStream.write(chunkData);
                outputStream.flush();
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }
}
