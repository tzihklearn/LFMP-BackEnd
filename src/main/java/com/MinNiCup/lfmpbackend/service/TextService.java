package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.FieldParam;

import java.util.List;

public interface TextService {

    CommonResult<String> field(List<FieldParam> fieldParams);
}
