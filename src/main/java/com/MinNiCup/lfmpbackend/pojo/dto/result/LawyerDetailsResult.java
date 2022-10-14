package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
public class LawyerDetailsResult {

    Integer lawyerId;

    String name;

    String avatarUrl;

    Integer year;

    String phone;

    String address;

    String intro;

    String realm;

    String viceRealm;

}
