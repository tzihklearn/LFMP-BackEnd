package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.FreeConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerResult;

import java.util.List;

public interface ConsumerService {

    CommonResult<String> modifyName(ModifyNameParam modifyNameParam);

    CommonResult<List<FreeConsultResult>> freeConsult();

}
