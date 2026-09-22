package com.example.kd.validate.api.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipExample {
    public static void main(String[] args) {
        String zipFilePath = "zpy-result1.zip"; // ZIP文件路径
        String destDirectory = "C:\\Users\\zl108\\Desktop\\Validate\\Validate\\unzip"; // 解压目标目录

        unzip(zipFilePath, destDirectory);
    }

    public static void unzip(String zipFilePath, String destDirectory) {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try{
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            // 读取zip文件中的每个条目（文件或文件夹）
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // 如果是文件，则解压到目标目录
                    extractFile(zipIn, filePath);
                } else {
                    // 如果是目录，则创建目录结构
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try{
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }catch (Exception e)
        {

        }
    }
}
