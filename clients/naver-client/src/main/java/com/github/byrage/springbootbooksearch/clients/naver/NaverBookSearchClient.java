package com.github.byrage.springbootbooksearch.clients.naver;

import com.github.byrage.springbootbooksearch.clients.naver.dto.NaverBookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.naver.exception.NaverClientException;
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
public class NaverBookSearchClient {

    private static final String BOOK_SEARCH_PATH = "/v1/search/book.json?query={query}&start={start}&display={display}";
    private final RestTemplate naverClientRestTemplate;

    public ResponseEntity<NaverBookSearchResponse> inquireNaverBookSearch(String keyword, int start, int display) {
        Map<String, Object> uriVariables = buildUriVariables(keyword, start, display);
        URI uri = naverClientRestTemplate.getUriTemplateHandler()
                .expand(BOOK_SEARCH_PATH, uriVariables);

        try {
            return naverClientRestTemplate.exchange(uri, HttpMethod.GET, null, NaverBookSearchResponse.class);
        } catch (Exception e) {
            log.error("[Naver] 책 검색 API 실패. keyword={}, start={}, display={}", keyword, start, display, e);
            throw new NaverClientException("[Naver] 책 검색 API 실패. keyword=" + keyword + ", start=" + start + ", display=" + display, e);
        }
    }

    private Map<String, Object> buildUriVariables(String query, int start, int display) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("query", query);
        uriVariables.put("start", start);
        uriVariables.put("display", display);
        return uriVariables;
    }
}
