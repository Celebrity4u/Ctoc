package com.example.kd.validate.api.req;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @创建人 ZL
 * @创建时间 2025/5/23
 * @描述 评测任务请求参数
 */
@Data
@Accessors(chain = true)
public class EvaluateREQ implements Serializable {
    private static final long serialVersionUID = 1L;
    //@ApiModelProperty(value = "房间号")
    @NotBlank(message = "唯一请求标识，任务编号")
    private String request_id;
    @NotBlank(message = "提交文件下载地址")
    //@ApiModelProperty(value = "操作密码")
    private String file_url;
    // 题型:1=甲题,2=乙题;不传默认按甲题处理
    private String topic_type;
}
