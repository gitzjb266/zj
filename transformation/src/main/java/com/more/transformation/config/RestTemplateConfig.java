package com.more.transformation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lzy
 * @date 2021/7/26 10:37
 */
@Configuration
public class RestTemplateConfig {
    @Bean("restTmp")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
