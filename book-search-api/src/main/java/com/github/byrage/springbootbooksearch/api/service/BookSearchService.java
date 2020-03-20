package com.github.byrage.springbootbooksearch.api.service;

import com.github.byrage.springbootbooksearch.api.dto.BookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.kakao.KakaoBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.naver.NaverBookSearchClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BookSearchService {

    private final KakaoBookSearchClient kakaoBookSearchClient;
    private final NaverBookSearchClient naverBookSearchClient;

    public BookSearchService(KakaoBookSearchClient kakaoBookSearchClient, NaverBookSearchClient naverBookSearchClient) {
        this.kakaoBookSearchClient = kakaoBookSearchClient;
        this.naverBookSearchClient = naverBookSearchClient;
    }

    public BookSearchResponse searchBook(String keyword, int page, int size) {
        CompletableFuture<BookSearchResponse> kakaoBookSearchCompletableFuture = searchBookFromKakao(keyword, page, size);
        return kakaoBookSearchCompletableFuture
                .handle((bookSearchResponse, exception) -> exception)
                .thenCompose(exception -> exception == null ? kakaoBookSearchCompletableFuture : searchBookFromNaver(keyword, page, size))
                .join();
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
