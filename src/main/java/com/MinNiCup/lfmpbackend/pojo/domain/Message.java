package com.MinNiCup.lfmpbackend.pojo.domain;

import lombok.Data;

@Data
public class Message {
    private Integer id;
    private Integer consultId;
    private Integer from;
    private Integer to;
    private Long date;
    private String message;
}
