package com.example.kd.validate.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : zl
 * @description : TODO
 * @date :2025/6/28 9:19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DataBo implements Serializable {
    @JsonProperty("N")
    @JSONField(name = "N")
    private String N;
    // 乙题:排行榜显示值F1(exe输出第二行)
    @JsonProperty("F1")
    @JSONField(name = "F1")
    private String F1;
    // 乙题:exe输出第三行原始值,F2由后端根据提交时间计算
    @JsonProperty("F2")
    @JSONField(name = "F2")
    private String F2;
}
