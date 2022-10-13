package com.MinNiCup.lfmpbackend.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 * @author tzih
 * @date 2022.09.30
 */
@Data
public class MultipartFileUploadConfiguration {
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


}
