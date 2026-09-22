package com.example.kd.validate.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : zl
 * @description : TODO
 * @date :2025/6/28 9:19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResultBo implements Serializable {
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private DataBo data;
}
