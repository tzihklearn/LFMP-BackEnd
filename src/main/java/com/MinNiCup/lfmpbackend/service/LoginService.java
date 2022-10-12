package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginTokenParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.RegisterParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.VerifyParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LoginResult;

public interface LoginService {

    CommonResult<LoginResult> loginByPassword(LoginParam loginParam);

    CommonResult<String> register(RegisterParam registerParam);

    CommonResult<String> verifyAndRegister(VerifyParam verifyParam);

    CommonResult<LoginResult> loginByToken(LoginTokenParam loginTokenParam);

}
