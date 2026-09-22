package com.example.kd.validate.api.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.fileDataLocalPath}")
    private String fileDataLocalPath;
    @Value("${file.fileDownLocalPath}")
    private String fileDownLocalPath;

    @Value("${file.fileUpLocalPath}")
    private String fileUpLocalPath;

    @Value("${file.httpFilePath}")
    private String httpFilePath;

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath = new File(".").getCanonicalPath();
        File fileDataPath = new File(basePath + this.fileDataLocalPath);
        if (!fileDataPath.exists()){
            fileDataPath.mkdirs();
        }
        File fileLocalPath = new File(basePath + this.fileDownLocalPath);
        if (!fileLocalPath.exists()){
            fileLocalPath.mkdirs();
        }
        File fileUpLocalPath = new File(basePath + this.fileUpLocalPath);
        if (!fileUpLocalPath.exists()){
            fileUpLocalPath.mkdirs();
        }
        registry.addResourceHandler(httpFilePath +"**").addResourceLocations("file:"+fileLocalPath.getPath()+File.separator);
    }
}
