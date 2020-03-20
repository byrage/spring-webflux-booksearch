package com.github.byrage.springbootbooksearch.clients.kakao;

import com.github.byrage.springbootbooksearch.clients.kakao.dto.KakaoBookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.kakao.exception.KakaoClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoBookSearchClient {

    private static final String BOOK_SEARCH_PATH = "/v3/search/book?query={query}&page={page}&size={size}";
    private final RestTemplate kakaoClientRestTemplate;

    public ResponseEntity<KakaoBookSearchResponse> inquireKakaoBookSearch(String keyword, int page, int size) {
        Map<String, Object> uriVariables = buildUriVariables(keyword, page, size);
        URI uri = kakaoClientRestTemplate.getUriTemplateHandler()
                .expand(BOOK_SEARCH_PATH, uriVariables);

        try {
            return kakaoClientRestTemplate.exchange(uri, HttpMethod.GET, null, KakaoBookSearchResponse.class);
        } catch (Exception e) {
            log.error("[Kakao] 책 검색 API 실패. keyword={}, page={}, size={}", keyword, page, size, e);
            throw new KakaoClientException("[Kakao] 책 검색 API 실패. keyword=" + keyword + ", page=" + page + ", size=" + size, e);
        }
    }

    private Map<String, Object> buildUriVariables(String query, int page, int size) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("query", query);
        uriVariables.put("page", page);
        uriVariables.put("size", size);
        return uriVariables;
    }
}
