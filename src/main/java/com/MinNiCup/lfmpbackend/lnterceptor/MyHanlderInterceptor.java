package com.MinNiCup.lfmpbackend.lnterceptor;

import com.MinNiCup.lfmpbackend.mapper.UserMapper;
import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.utils.JwtUtil;
import com.MinNiCup.lfmpbackend.utils.RedisUtils.RedisUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tzih
 * @date 2022.09.13
 */
@Component
@Slf4j
public class MyHanlderInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("Authorization");

        if (token == null) {
            log.info("没有token");
            return false;
        }

        DecodedJWT decode = JwtUtil.decode(token);
//        String userId = request.getParameter("userId");
//&& !decode.getClaims().get("userId").toString().equals(userId)
        if (decode != null ) {

            String toString = decode.getClaim("account").toString();
            String account = toString.replaceAll("\"", "");

            log.info("从redis里查询用户");
            Object o = redisUtil.get(account);
            if (o != null) {
                String str = o.toString();

                if (str.equals(token)) {

                    log.info("从数据库里查询用户");
                    User user = userMapper.selectAllByAccount(account);

                    if (user != null) {

                        log.info("将登录用户放到ThreadLocal变量变量中");
                        //将登录用户放到ThreadLocal变量变量中，方便业务获取当前登录用户
                        CurrentUser currentUser = new CurrentUser();

                        currentUser.setId(user.getId());
                        currentUser.setAccount(user.getAccount());
                        currentUser.setIdIdent(user.getIdIdent());

                        CurrentUserUtil.setCurrentUser(currentUser);

                        log.info("token校验通过");
                        return true;

                    }
                    else {
                        log.info("数据库里没有该用户");
                    }
                }
                else {
                    log.info("redis中token不匹配");
                }
            }
            else {
                log.info("redis里没有该用户");
            }
        }

        log.warn("token校验未通过");

        CommonResult<String> objectCommonResult = CommonResult.userAuthError();

        try {
            //将 map装换为json ResponseBody底层使用jackson
            String json = new ObjectMapper().writeValueAsString(objectCommonResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;


//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
