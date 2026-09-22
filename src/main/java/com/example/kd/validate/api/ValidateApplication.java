package com.example.kd.validate.api;

import com.example.kd.validate.api.data.Constants;
import com.example.kd.validate.api.utils.StringUtils;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class ValidateApplication {

    public static void main(String[] args) {
        String mainServiceIp ="172.16.30.36";
        for(String argsItem:args) {
            if(argsItem.indexOf("--mainServiceIp") >-1)
            {
                mainServiceIp = argsItem;
                mainServiceIp = mainServiceIp.substring(mainServiceIp.indexOf("=")+1,mainServiceIp.length());
                Constants.mainServiceIp = mainServiceIp;
            }
            if(argsItem.indexOf("--main.ssl.enabled") >-1)
            {
                String openHttps = argsItem;
                openHttps = openHttps.substring(openHttps.indexOf('=')+1,openHttps.length());
                if(StringUtils.isNotEmpty(openHttps))
                {
                    if("true".equals(openHttps))
                    {
                        Constants.openMainHttps = true;
                    }else{
                        Constants.openMainHttps = false;
                    }
                }
            }
            if(argsItem.indexOf("--server.ssl.enabled") >-1)
            {
                String openHttps = argsItem;
                openHttps = openHttps.substring(openHttps.indexOf('=')+1,openHttps.length());
                if(StringUtils.isNotEmpty(openHttps))
                {
                    if("true".equals(openHttps))
                    {
                        Constants.openMyHttps = true;
                    }else{
                        Constants.openMyHttps = false;
                    }
                }
            }

            if(argsItem.indexOf("--file.delete") >-1)
            {
                String delete = argsItem;
                delete = delete.substring(delete.indexOf('=')+1,delete.length());
                if(StringUtils.isNotEmpty(delete))
                {
                    if("true".equals(delete))
                    {
                        Constants.fileDelete = true;
                    }else{
                        Constants.fileDelete = false;
                    }
                }
            }
            if(argsItem.indexOf("--encode") >-1)
            {
                String encode = argsItem;
                encode = encode.substring(encode.indexOf('=')+1,encode.length());
                if(StringUtils.isNotEmpty(encode))
                {
                    if("true".equals(encode))
                    {
                        Constants.encode = true;
                    }else{
                        Constants.encode = false;
                    }
                }
            }
        }
        SpringApplication.run(ValidateApplication.class, args);
    }

}
