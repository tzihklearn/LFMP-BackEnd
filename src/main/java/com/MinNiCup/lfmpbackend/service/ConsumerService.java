package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;

public interface ConsumerService {

    CommonResult<String> modifyName(ModifyNameParam modifyNameParam);

}
