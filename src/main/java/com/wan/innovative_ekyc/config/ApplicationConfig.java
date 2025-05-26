package com.wan.innovative_ekyc.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application")
@Getter
public class ApplicationConfig {
    private final Innovative innovative = new Innovative();

    @Data
    private static class Innovative {
        private String url;
        private String username;
        private String password;
        private String apiKey;
        private String okIdApiKey;
        private String okDocApiKey;
        private String okFaceApiKey;
        private String okLiveApiKey;
    }
}
