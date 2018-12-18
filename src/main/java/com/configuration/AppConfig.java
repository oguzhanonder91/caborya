package com.configuration;

import com.common.security.ActiveUserStore;
import com.common.util.CommonMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by oguzhanonder - 29.10.2018
 */
@Configuration
public class AppConfig {

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

    @Bean
    public CommonMethod commonMethod() {
        return new CommonMethod();
    }

}