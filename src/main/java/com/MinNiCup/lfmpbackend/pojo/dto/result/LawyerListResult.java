package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @date 2022.10.14
 */
@Data
public class LawyerListResult {

    List<LawyerResult> lawyerResults;

    Integer page;

}
