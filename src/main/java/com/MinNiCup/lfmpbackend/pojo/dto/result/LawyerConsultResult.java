package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
public class LawyerConsultResult {

    Integer consultId;

    String name;

    String phone;

    String data;

    Integer isReply;

}
