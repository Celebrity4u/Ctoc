package com.example.kd.validate.api.data;

import com.example.kd.validate.api.data.ResultBo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : zl
 * @description : TODO
 * @date :2025/6/28 9:44
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DataBoX implements Serializable {
    @JsonProperty("request_id")
    private String request_id;
    @JsonProperty("result")
    private ResultBo result;
}
