package com.example.kd.validate.api.resq;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @创建人 ZL
 * @创建时间 2025/5/23
 * @描述 评测任务请求参数
 */
@Data
@Accessors(chain = true)
public class EvaluateData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String request_id;
    private String file_url;
    private String data_path;

    private String down_path;
}
