package com.example.kd.validate.api.control;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查接口:供平台探活与本地守护脚本(guard.bat)使用。
 * 能返回即说明进程存活且Tomcat正常响应;附带检查工作目录下两个验证exe是否在位,便于发现部署缺失。
 */
@CrossOrigin
@RestController
public class HealthController {

    @GetMapping({"/api/health", "/health"})
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "ok");
        result.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        File workDir = new File(System.getProperty("user.dir"));
        result.put("verify_score.exe", new File(workDir, "verify_score.exe").exists());
        result.put("AtCTOC14Main.exe", new File(workDir, "AtCTOC14Main.exe").exists());
        return result;
    }
}
