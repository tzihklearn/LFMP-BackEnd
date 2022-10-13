package com.MinNiCup.lfmpbackend.service.impl;

import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.mapper.UserMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginTokenParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.RegisterParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.VerifyParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LoginResult;
import com.MinNiCup.lfmpbackend.pojo.po.UserPo;
import com.MinNiCup.lfmpbackend.service.LoginService;
import com.MinNiCup.lfmpbackend.utils.JwtUtil;
import com.MinNiCup.lfmpbackend.utils.MD5Util;
import com.MinNiCup.lfmpbackend.utils.RandomUtil;
import com.MinNiCup.lfmpbackend.utils.RedisUtils.RedisUtil;
import com.MinNiCup.lfmpbackend.utils.SmsUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author tzih
 * @date 2022.10.10
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final Integer over_time = 3;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;

//    @Resource
//    private UserMapper<LoginResult> userLoginMapper;

    @Resource
    private UserInfoMapper userInfoMapper;



    @Override
    public CommonResult<LoginResult> loginByPassword(LoginParam loginParam) {

        log.info("密码检验");

        String md5Str;
        try {
            md5Str = MD5Util.getMD5Str(loginParam.getPassword());
        } catch (NoSuchAlgorithmException e) {
            log.error("密码加密错误");
            throw new RuntimeException(e);
        }

        loginParam.setPassword(md5Str);

        UserPo userPo = userMapper.selectByLogin(loginParam);

        if (userPo == null) {
            log.info("用户密码错误或不存在");
            return CommonResult.userPasswordWrong();
        }

        //设置token
        String token = setToken(loginParam.getAccount());

        LoginResult loginResult = new LoginResult();

        loginResult.setAccount(loginParam.getAccount());
        loginResult.setName(userPo.getName());
        loginResult.setIsIdent(userPo.getIsIdent());
        loginResult.setToken(token);

        return CommonResult.success(loginResult);
    }

    @Override
    public CommonResult<String> register(RegisterParam registerParam) {

        String randomUtil = RandomUtil.getRandomUtil(6);

        String[] templateParamSet = {randomUtil, over_time.toString()};

        String code = SmsUtil.sendSms(registerParam.getPhone(), templateParamSet);

        log.info("发送短信中");

        if (!Objects.equals(code, "Ok")) {
            log.warn("发送短信失败" + code);
            return CommonResult.fail("发送短信失败");
        }

        boolean set = redisUtil.set(registerParam.getPhone(), randomUtil, over_time, TimeUnit.MINUTES);

        if (!set) {
            log.warn("redis设置失败");
            return CommonResult.fail("发送短信失败");
        }

        return CommonResult.success("短信发送成功");
    }

    @Override
    public CommonResult<String> verifyAndRegister(VerifyParam verifyParam) {

        String verify = redisUtil.get(verifyParam.getPhone()).toString();

        System.out.println(verify);
        if (verifyParam.getVerification().equals(verify)) {

            User user = userMapper.selectAllByAccount(verifyParam.getAccount());

            if (user != null) {
                return CommonResult.fail("注册失败，账户已存在");
            }

            String password;
            try {
                password = MD5Util.getMD5Str(verifyParam.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            int r1 = userMapper.insertUser(verifyParam.getAccount(), password);

            if (r1 != 1) {
                return CommonResult.fail("注册失败");
            }

            User register = userMapper.selectAllByAccount(verifyParam.getAccount());

            UserInfo userInfo = new UserInfo();

            userInfo.setUserId(register.getId());
            userInfo.setName("未命名");
            userInfo.setPhone(verifyParam.getPhone());
            userInfo.setIsLawyer(false);

            int r2 = userInfoMapper.insert(userInfo);

            if (r2 != 1) {
                return CommonResult.fail("注册失败");
            }

            return CommonResult.success("注册成功");
        }
        else {
            return CommonResult.fail("注册失败");
        }
    }

    @Override
    public CommonResult<LoginResult> loginByToken(LoginTokenParam loginTokenParam) {
        String token = request.getHeader("Authorization");
        DecodedJWT decode = JwtUtil.decode(token);
        if (decode == null) {
            return CommonResult.token_error();
        }

        String account = decode.getClaim("account").toString();

        String str = account.replaceAll("\"", "");

        if ( !str.equals(loginTokenParam.getAccount())) {
            return CommonResult.token_error();
        }

        LoginResult loginResult = userMapper.selectLoginByAccount(str);

        return CommonResult.success(loginResult);

//        List<User> users = userMapper.selectByMap(map);
//
//        if (users.size() != 1) {
//            return CommonResult.userResourceException();
//        }
//
//        User user = users.get(0);
//
//        LoginResult result =  new LoginResult();
//
//        result.setAccount(loginTokenParam.getAccount());
//        result.setName();
//
//        return CommonResult.success(users.get(0));
    }

    private String setToken(String account) {
        log.info("生成token");
        Map<String, String> payload = new HashMap<>();

        payload.put("account", account);

//            payload.put("phone", verifyParam.getPhone());

        String token =  JwtUtil.getToken(payload);

        redisUtil.remove(account);

        redisUtil.set(account, token, 7 ,TimeUnit.DAYS);

        return token;
    }

}
