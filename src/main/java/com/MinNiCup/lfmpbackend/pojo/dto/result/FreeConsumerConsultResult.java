package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.15
 */
@Data
public class FreeConsumerConsultResult {

    Integer consultId;

    String name;

    String phone;

    String field;

    String address;

    String data;

    String reviewData;

    Integer isReply;

}
