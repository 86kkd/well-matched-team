package com.versionone.demo1server.utils;



import java.io.*;

public class Base64Util {
    /**
     * 本地图片转base64
     */
    public static String getImgFileToBase642(byte[] buffer) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        // 对字节数组Base64编码
        java.util.Base64.Encoder encode = java.util.Base64.getEncoder();
        return encode.encodeToString(buffer);
    }


    /**
     * base64转图片
     * @param imgBase64 图片base64
     */
    public static byte[] getImgBase64ToImgFile(String imgBase64) {
        java.util.Base64.Decoder decode = java.util.Base64.getDecoder();
            // 解密处理数据
            byte[] bytes = decode.decode(imgBase64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
        return bytes;
    }


}
