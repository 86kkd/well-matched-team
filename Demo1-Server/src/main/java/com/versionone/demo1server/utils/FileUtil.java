package com.versionone.demo1server.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件类
 */
public class FileUtil {
    /**
     * file转byte
     */
    public static byte[] fileToByte(File file){
        byte[] buffer = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * byte 转file
     */
    public static File byteToFile(byte[] buf, String filePath, String fileName){
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try{
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()){
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if (bos != null){
                try{
                    bos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try{
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
    /**
     * multipartFile转File
     **/
    public static File multipartFileToFile(MultipartFile multipartFile){
        File file = null;
        if (multipartFile != null){
            try {
                file=File.createTempFile("tmp", null);
                multipartFile.transferTo(file);
                System.gc();
                file.deleteOnExit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return file;
    }
}
