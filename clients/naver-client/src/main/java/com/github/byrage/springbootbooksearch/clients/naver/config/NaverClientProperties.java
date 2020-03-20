package com.github.byrage.springbootbooksearch.clients.naver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("naver.client")
public class NaverClientProperties {
    private String protocol;
    private String url;
    private String xNaverClientId;
    private String xNaverClientSecret;
    private Long readTimeoutMillis;
    private Long connectionTimeoutMillis;
}
