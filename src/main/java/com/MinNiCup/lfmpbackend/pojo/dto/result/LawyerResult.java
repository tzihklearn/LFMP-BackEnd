package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Data
@AllArgsConstructor
public class LawyerResult {

    Integer lawyerId;

    String name;

    String avatarUrl;

    String intro;

}
