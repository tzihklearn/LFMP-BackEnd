package com.MinNiCup.lfmpbackend.pojo.domain;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
public class Consult {

    Integer id;

    Integer consumerId;

    Integer lawyerId;

    String data;

    Integer model;

    Integer isReply;

}
