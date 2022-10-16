package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LawyerInformationParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ReviewFreeConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerAvatarResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerInformationResult;

import java.util.List;

public interface LawyerService {

    CommonResult<LawyerInformationResult> check();

    CommonResult<String> modify(LawyerInformationParam lawyerInformationParam);

    CommonResult<LawyerAvatarResult> checkAvatar();

    CommonResult<List<LawyerConsultResult>> freeConsult();

    CommonResult<List<LawyerConsultResult>> onlineConsult();

    CommonResult<List<LawyerConsultResult>> phoneConsult();

    CommonResult<String> reviewFreeConsult(ReviewFreeConsultParam reviewFreeConsultParam);

}
