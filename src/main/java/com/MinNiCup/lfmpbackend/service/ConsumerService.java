package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CommitConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerNameResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerConsultResult;

import java.util.List;

public interface ConsumerService {

    CommonResult<ConsumerNameResult> check();

    CommonResult<String> modifyName(ModifyNameParam modifyNameParam);

    CommonResult<List<ConsumerConsultResult>> freeConsult();

    CommonResult<List<ConsumerConsultResult>> onlineConsult();

    CommonResult<List<ConsumerConsultResult>> phoneConsult();

    CommonResult<String> commitConsult(CommitConsultParam commitConsultParam);

}
