package com.MinNiCup.lfmpbackend.lnterceptor;

import com.MinNiCup.lfmpbackend.mapper.UserMapper;
import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.pojo.dto.param.WsConnectParam;
import com.MinNiCup.lfmpbackend.utils.WebSocket.AttributesKeys;
import com.MinNiCup.lfmpbackend.utils.WebSocket.ConsultUtil;
import com.MinNiCup.lfmpbackend.utils.JwtUtil;
import com.MinNiCup.lfmpbackend.utils.RedisUtils.RedisUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class WsInterceptor implements HandshakeInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;
    @Autowired
    MyHanlderInterceptor myHanlderInterceptor;
    /**
     * Invoked before the handshake is processed.
     *
     * @param request    the current request
     * @param response   the current response
     * @param wsHandler  the target WebSocket handler
     * @param attributes the attributes from the HTTP handshake to associate with the WebSocket
     *                   session; the provided attributes are copied, the original map is not used.
     * @return whether to proceed with the handshake ({@code true}) or abort ({@code false})
     */
    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) throws Exception {

        String token = null;
        try {
            token = Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0);
        } catch (NullPointerException e){
//            log.info(e.getMessage());
        }

        if (token == null) {
            log.info("没有token");
            return false;
        }

        DecodedJWT decode = JwtUtil.decode(token);
        int paramCheck = 0;
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
                        InputStream body = request.getBody();
                        ObjectMapper objectMapper = new ObjectMapper();
                        WsConnectParam param = null;
                        try {
                            param = objectMapper.readValue(body, WsConnectParam.class);
                            body.close();
                        } catch (Exception e){
                            log.warn(e.getMessage());
                            log.warn(Arrays.toString(e.getStackTrace()));
                        }
                        if (param != null){
                            Consult consult = ConsultUtil.checkUserAndConsult(param, user);
                            if (consult.getId() != 0){
                                if (consult.getIsReply() == 2)
                                    attributes.put(AttributesKeys.AC.getName(), "YES");
                                attributes.put(AttributesKeys.CONSULT.getName(), consult);
                                log.info("将登录用户放到attributes中");
                                attributes.put(AttributesKeys.USER.getName(), user);
                                log.info("校验通过");
                                return true;
                            }
                            else {
                                log.info("咨询与当前用户不匹配");
                                paramCheck = 2;
                            }                        }
                        else {
                            log.info("未查询到咨询信息");
                            paramCheck = 1;
                        }
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
        CommonResult objectCommonResult;
        switch (paramCheck){
            case 1:
                objectCommonResult = CommonResult.fail("未查询到对应咨询");
                break;
            case 2:
                objectCommonResult = CommonResult.fail("咨询与当前登录用户不符");
                break;
            default:
                objectCommonResult = CommonResult.userAuthError();

        }

        try {
            //将 map装换为json ResponseBody底层使用jackson
            String json = new ObjectMapper().writeValueAsString(objectCommonResult);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            response.getBody().flush();
            response.getBody().write(json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.warn(e.getMessage());
            log.warn(Arrays.toString(e.getStackTrace()));
        }
        return false;


//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * Invoked after the handshake is done. The response status and headers indicate
     * the results of the handshake, i.e. whether it was successful or not.
     *
     * @param request   the current request
     * @param response  the current response
     * @param wsHandler the target WebSocket handler
     * @param exception an exception raised during the handshake, or {@code null} if none
     */
    @Override
    public void afterHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}