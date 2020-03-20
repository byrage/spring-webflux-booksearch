package com.github.byrage.springbootbooksearch.api.service;

import com.github.byrage.springbootbooksearch.api.dto.BookSearchResult;
import com.github.byrage.springbootbooksearch.clients.kakao.KakaoBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.kakao.dto.KakaoBookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.kakao.dto.Meta;
import com.github.byrage.springbootbooksearch.clients.naver.NaverBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.naver.dto.NaverBookSearchResponse;
import com.github.byrage.springbootbooksearch.domain.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private static final long EMPTY_TOTAL_COUNT = 0L;

    private final KakaoBookSearchClient kakaoBookSearchClient;
    private final NaverBookSearchClient naverBookSearchClient;
    private final SearchHistoryService searchHistoryService;

    public BookSearchResult searchBook(String memberId, String keyword, int page, int size) {
        CompletableFuture<BookSearchResult> kakaoBookSearchCompletableFuture = searchBookFromKakao(keyword, page, size);
        BookSearchResult response = kakaoBookSearchCompletableFuture
                .handle((bookSearchResult, exception) -> exception)
                .thenCompose(exception -> exception == null ? kakaoBookSearchCompletableFuture : searchBookFromNaver(keyword, page, size))
                .join();
        searchHistoryService.saveSearchHistory(memberId, keyword, LocalDateTime.now());
        return response;
    }

    private CompletableFuture<BookSearchResult> searchBookFromKakao(String keyword, int page, int size) {
        return CompletableFuture.supplyAsync(() -> kakaoBookSearchClient.inquireKakaoBookSearch(keyword, page, size))
                .thenApply(kakaoBookSearchResponse -> BookSearchResult.builder()
                        .totalCount(Optional.ofNullable(kakaoBookSearchResponse)
                                .map(KakaoBookSearchResponse::getMeta)
                                .map(Meta::getTotalCount)
                                .orElse(EMPTY_TOTAL_COUNT))
                        .currentPage(page)
                        .data(Optional.ofNullable(kakaoBookSearchResponse)
                                .map(KakaoBookSearchResponse::getDocuments)
                                .orElse(null))
                        .build());
    }

    private CompletableFuture<BookSearchResult> searchBookFromNaver(String keyword, int page, int size) {
        return CompletableFuture.supplyAsync(() -> naverBookSearchClient.inquireNaverBookSearch(keyword, page, size))
                .thenApply(naverBookSearchResponse -> BookSearchResult.builder()
                        .totalCount(Optional.ofNullable(naverBookSearchResponse)
                                .map(NaverBookSearchResponse::getTotal)
                                .orElse(EMPTY_TOTAL_COUNT))
                        .currentPage(page)
                        .data(Optional.ofNullable(naverBookSearchResponse)
                                .map(NaverBookSearchResponse::getItems)
                                .orElse(null))
                        .build());
    }
}
