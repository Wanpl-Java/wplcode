package com.wplcode.wplcode.config;

import io.github.briqt.spark4j.SparkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XunFeiConfig {

    @Bean
    public SparkClient sparkClient() {
        SparkClient sparkClient = new SparkClient();
        sparkClient.appid = "40106723";
        sparkClient.apiSecret = "MGExNWM5NWY5M2FlYjg2M2FjYmE5OTkx";
        sparkClient.apiKey = "3b5990c14e0127039fdc9e9c11681856";
        return sparkClient;
    }
}
