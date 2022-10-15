package com.MinNiCup.lfmpbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class WsConnectParam {
    @NotNull(message = "不能为空")
    @Min(value = 1, message = "格式不正确")
    private Integer consultId;
}
