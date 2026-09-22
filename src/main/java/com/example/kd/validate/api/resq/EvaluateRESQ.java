package com.example.kd.validate.api.resq;


import com.example.kd.validate.api.data.DataBoX;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @创建人 ZL
 * @创建时间 2025/5/23
 * @描述 评测任务返回参数
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class EvaluateRESQ implements Serializable {
    private static final long serialVersionUID = 1L;


    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private DataBoX data;
}
