package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerResult;

import java.util.List;

/**
 * @author tzih
 * @date 2022.10.13
 */
public interface HomepageService {

    CommonResult<List<LawyerResult>> getLawyerList();

    CommonResult<List<LawyerResult>> lawyerSearch(String keyWord);

}
