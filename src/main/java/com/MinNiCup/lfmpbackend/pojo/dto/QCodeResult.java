package com.MinNiCup.lfmpbackend.pojo.dto;

import lombok.Data;

@Data
public class QCodeResult {
    private String message;
    private String code;
    private Result data;

    @Data
    public static class Result{
        private String picName;
    }
}
