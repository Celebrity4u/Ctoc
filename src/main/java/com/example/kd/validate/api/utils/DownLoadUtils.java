package com.example.kd.validate.api.utils;

import cn.hutool.http.HttpUtil;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownLoadUtils
{


    public static void main(String[] args) {
        downloadByNIO("http://localhost:8089/zpy-result1.xml","C:\\Users\\zl108\\Desktop\\Validate\\Validate\\unzip","123344.xml");
    }
    /**
     * 下载文件
     * @param url
     * @param saveDir
     * @param fileName
     */
    public static void downloadByNIO(String url, String saveDir, String fileName) {
        HttpUtil.downloadFile(url,saveDir+fileName);
        /*try{
            HttpUtil.downloadFile(url,saveDir+fileName);
            InputStream ins = new UrlResource(url).getInputStream();
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("downloadByNIO error from remoteUrl", e);
        }*/
    }

}
