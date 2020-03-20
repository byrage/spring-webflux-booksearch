package com.github.byrage.springbootbooksearch.clients.naver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(NaverClientProperties.class)
public class NaverClientConfig {

    private static final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    private static final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

    private final NaverClientProperties naverClientProperties;

    @Bean
    public RestTemplate naverClientRestTemplate() {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(buildUriBuilder(naverClientProperties))
                .setConnectTimeout(Duration.ofMillis(naverClientProperties.getConnectionTimeoutMillis()))
                .setReadTimeout(Duration.ofMillis(naverClientProperties.getReadTimeoutMillis()))
                .build();

        restTemplate.getInterceptors()
                .add(this::setNaverClientHeaders);

        return restTemplate;
    }

    private ClientHttpResponse setNaverClientHeaders(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.set(X_NAVER_CLIENT_ID, naverClientProperties.getXNaverClientId());
        headers.set(X_NAVER_CLIENT_SECRET, naverClientProperties.getXNaverClientSecret());
        return execution.execute(request, body);
    }

    private DefaultUriBuilderFactory buildUriBuilder(NaverClientProperties naverClientProperties) {
        return new DefaultUriBuilderFactory(UriComponentsBuilder.newInstance()
                .scheme(naverClientProperties.getProtocol())
                .host(naverClientProperties.getUrl()));
    }
}
