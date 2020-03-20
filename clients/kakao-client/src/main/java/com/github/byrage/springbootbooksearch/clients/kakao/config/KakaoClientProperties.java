package com.github.byrage.springbootbooksearch.clients.kakao.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kakao.client")
public class KakaoClientProperties {
    private String protocol;
    private String url;
    private String authorization;
    private Long readTimeoutMillis;
    private Long connectionTimeoutMillis;
}
