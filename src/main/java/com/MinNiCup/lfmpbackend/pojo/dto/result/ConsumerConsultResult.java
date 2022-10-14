package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
public class ConsumerConsultResult {

    Integer consultId;

    String name;

    String phone;

    String address;

    String data;

    Integer isReply;

}
