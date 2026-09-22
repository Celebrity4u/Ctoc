package com.example.kd.validate.api.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtil {
    public static void writeFile(String str, String path, String name,Boolean addTime) {
        try {
            File file = new File(path + File.separator + name);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(path + File.separator + name);
            if(addTime)
            {
                fw.write("Log---" + new Date() + "------" + str);
            }else{
                fw.write(str);
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取txt中的内容，将内容添加到set集合中
     * @author zl
     * @date 2022年06月06日 下午08:50:06
     * @return
     * @version 1.0
     * @throws Exception
     */
    public static Set<String> readTxtFile(String path){
        Set<String> set = new HashSet<String>();
        InputStreamReader read = null;
        File file = new File(path);    //读取文件
        try {
            read = new InputStreamReader(new FileInputStream(file),"UTF-8");
            if(file.isFile() && file.exists()){      //文件流是否存在
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            }
            else{         //不存在抛出异常信息
                throw new Exception("文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                read.close();     //关闭文件流
            }catch (Exception e)
            {

            }

        }
        return set;
    }
    public static void  copyDir(String oldPath,String newPath) throws IOException
    {
        File file = new File(oldPath);
        String[] filePath = file.list();
        if(!(new File(newPath).exists()))
        {
            (new File((newPath))).mkdir();
        }
        for(int i = 0 ;i < filePath.length ;i++)
        {
            if((new File(oldPath +File.separator+filePath[i])).isDirectory())
            {
                copyDir(oldPath+File.separator+filePath[i],newPath +File.separator+filePath[i]);
            }
            if((new File(oldPath +File.separator+filePath[i])).isFile())
            {
                copyFile(oldPath+File.separator+filePath[i],newPath +File.separator+filePath[i]);
            }
        }
    }
    public static void copyFile(String oldPath,String newPath )throws IOException
    {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0)
            {
                out.write(buffer,0,bytesRead);
            }
        }catch (Exception e)
        {
            //e.printStackTrace();
        }finally {
            in.close();
            out.close();
            in = null;
            out = null;
        }

    }
}

