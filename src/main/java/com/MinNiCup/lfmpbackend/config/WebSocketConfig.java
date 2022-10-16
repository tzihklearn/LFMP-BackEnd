package com.MinNiCup.lfmpbackend.config;

import com.MinNiCup.lfmpbackend.WSClasses.HttpAuthHandler;
import com.MinNiCup.lfmpbackend.lnterceptor.WsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private HttpAuthHandler httpAuthHandler;
    @Resource
    private WsInterceptor wsInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "/wstalk")
                .addInterceptors(wsInterceptor);
    }
}
