package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginTokenParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.RegisterParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.VerifyParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LoginResult;
import com.MinNiCup.lfmpbackend.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.10.09
 */
@RestController
@RequestMapping("/login")
public class loginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public CommonResult<LoginResult> loginByPassword(@RequestBody LoginParam loginParam) {
        return loginService.loginByPassword(loginParam);
    }

    @PostMapping("/register")
    public CommonResult<String> register(@RequestBody RegisterParam registerParam) {
        return loginService.register(registerParam);
    }

    @PostMapping("/verify/register")
    public CommonResult<String> verifyAndRegister(@RequestBody VerifyParam verifyParam) {
        return loginService.verifyAndRegister(verifyParam);
    }

    @PostMapping("/token")
    public CommonResult<LoginResult> loginByToken(@RequestBody LoginTokenParam loginTokenParam) {
        return loginService.loginByToken(loginTokenParam);
    }
}
