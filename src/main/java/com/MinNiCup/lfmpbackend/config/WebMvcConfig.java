package com.MinNiCup.lfmpbackend.config;

import com.MinNiCup.lfmpbackend.lnterceptor.MyHanlderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private MyHanlderInterceptor myHanlderInterceptor;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截的请求和不拦截的请求
        registry.addInterceptor(myHanlderInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/wstalk/*");
    }

}
