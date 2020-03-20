package com.github.byrage.springbootbooksearch.api.service;

import com.github.byrage.springbootbooksearch.api.dto.BookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.kakao.KakaoBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.naver.NaverBookSearchClient;
import com.github.byrage.springbootbooksearch.domain.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final KakaoBookSearchClient kakaoBookSearchClient;
    private final NaverBookSearchClient naverBookSearchClient;
    private final SearchHistoryService searchHistoryService;

    public BookSearchResponse searchBook(String memberId, String keyword, int page, int size) {
        CompletableFuture<BookSearchResponse> kakaoBookSearchCompletableFuture = searchBookFromKakao(keyword, page, size);
        BookSearchResponse response = kakaoBookSearchCompletableFuture
                .handle((bookSearchResponse, exception) -> exception)
                .thenCompose(exception -> exception == null ? kakaoBookSearchCompletableFuture : searchBookFromNaver(keyword, page, size))
                .join();
        searchHistoryService.saveSearchHistory(memberId, keyword, LocalDateTime.now());
        return response;
    }

    private CompletableFuture<BookSearchResponse> searchBookFromKakao(String keyword, int page, int size) {
        return CompletableFuture.supplyAsync(() -> BookSearchResponse.builder()
                .data(kakaoBookSearchClient.inquireKakaoBookSearch(keyword, page, size))
                .build());
    }

    private CompletableFuture<BookSearchResponse> searchBookFromNaver(String keyword, int page, int size) {
        return CompletableFuture.supplyAsync(() -> BookSearchResponse.builder()
                .data(naverBookSearchClient.inquireNaverBookSearch(keyword, page, size))
                .build());
    }
}
