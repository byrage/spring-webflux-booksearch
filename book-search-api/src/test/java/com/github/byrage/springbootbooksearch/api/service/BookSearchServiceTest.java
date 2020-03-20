package com.github.byrage.springbootbooksearch.api.service;

import com.github.byrage.springbootbooksearch.clients.kakao.KakaoBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.naver.NaverBookSearchClient;
import com.github.byrage.springbootbooksearch.domain.service.SearchHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @InjectMocks
    private BookSearchService bookSearchService;

    @Mock
    private KakaoBookSearchClient kakaoBookSearchClient;
    @Mock
    private NaverBookSearchClient naverBookSearchClient;
    @Mock
    private SearchHistoryService searchHistoryService;

    @Test
    @DisplayName("카카오 API가 정상이라면 네이버 API를 호출하지 않는다")
    void notInquireNaverApiWhenKakaoApiIsNormal() {
        // when
        bookSearchService.searchBook("byrage", "테스트", 0, 1);

        // then
        then(kakaoBookSearchClient).should().inquireKakaoBookSearch(anyString(), anyInt(), anyInt());
        then(naverBookSearchClient).should(never()).inquireNaverBookSearch(anyString(), anyInt(), anyInt());
        then(searchHistoryService).should().saveSearchHistory(anyString(), anyString(), any(LocalDateTime.class));

    }

    @Test
    @DisplayName("카카오 API 예외 발생시 네이버 API를 호출한다")
    void inquireNaverApiWhenKakaoApiOccurredException() {
        // given
        given(kakaoBookSearchClient.inquireKakaoBookSearch(anyString(), anyInt(), anyInt()))
                .willThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // when
        bookSearchService.searchBook("byrage", "테스트", 0, 1);

        // then
        then(kakaoBookSearchClient).should().inquireKakaoBookSearch(anyString(), anyInt(), anyInt());
        then(naverBookSearchClient).should().inquireNaverBookSearch(anyString(), anyInt(), anyInt());
        then(searchHistoryService).should().saveSearchHistory(anyString(), anyString(), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("카카오, 네이버 API 예외 발생시 검색기록을 저장하지 않는다")
    void neverSaveSearchHistoryWhenBothApiOccurredException() {
        // given
        given(kakaoBookSearchClient.inquireKakaoBookSearch(anyString(), anyInt(), anyInt()))
                .willThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        given(naverBookSearchClient.inquireNaverBookSearch(anyString(), anyInt(), anyInt()))
                .willThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // when
        catchThrowable(() -> bookSearchService.searchBook("byrage", "테스트", 0, 1));

        // then
        then(kakaoBookSearchClient).should().inquireKakaoBookSearch(anyString(), anyInt(), anyInt());
        then(naverBookSearchClient).should().inquireNaverBookSearch(anyString(), anyInt(), anyInt());
        then(searchHistoryService).should(never()).saveSearchHistory(anyString(), anyString(), any(LocalDateTime.class));
    }
}