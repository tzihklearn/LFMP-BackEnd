package com.MinNiCup.lfmpbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
public class Consult {

    @TableId(type = IdType.AUTO)
    Integer id;

    Integer consumerId;

    Integer lawyerId;

    String data;

    String reviewData;

    Integer model;

    Integer isReply;

}
