package com.github.byrage.springbootbooksearch.clients.kakao.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(KakaoClientProperties.class)
public class KakaoClientConfig {

    private static final String AUTHORIZATION = "Authorization";

    @Bean
    public RestTemplate kakaoClientRestTemplate(KakaoClientProperties kakaoClientProperties) {

        return new RestTemplateBuilder()
                .uriTemplateHandler(buildUriBuilder(kakaoClientProperties))
                .defaultHeader(AUTHORIZATION, kakaoClientProperties.getAuthorization())
                .setConnectTimeout(Duration.ofMillis(kakaoClientProperties.getConnectionTimeoutMillis()))
                .setReadTimeout(Duration.ofMillis(kakaoClientProperties.getReadTimeoutMillis()))
                .build();
    }

    private DefaultUriBuilderFactory buildUriBuilder(KakaoClientProperties kakaoClientProperties) {
        return new DefaultUriBuilderFactory(UriComponentsBuilder.newInstance()
                .scheme(kakaoClientProperties.getProtocol())
                .host(kakaoClientProperties.getUrl()));
    }
}
