package com.MinNiCup.lfmpbackend.pojo.dto.param;

import lombok.Data;

/**
 * @author tzih
 * &#064;date  2022.10.10
 */
@Data
public class VerifyParam {

    String account;

    String password;

    String rePassword;

    String phone;

    String Verification;

}
