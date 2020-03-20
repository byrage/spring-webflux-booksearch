package com.github.byrage.springbootbooksearch.api.service;

import com.github.byrage.springbootbooksearch.api.dto.BookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.kakao.KakaoBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.kakao.dto.KakaoBookSearchResponse;
import com.github.byrage.springbootbooksearch.clients.naver.NaverBookSearchClient;
import com.github.byrage.springbootbooksearch.clients.naver.dto.NaverBookSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    @DisplayName("카카오 API가 정상이라면 네이버 API를 호출하지 않는다")
    void notInquireNaverApiWhenKakaoApiIsNormal() {
        // given
        given(kakaoBookSearchClient.inquireKakaoBookSearch(anyString(), anyInt(), anyInt()))
                .willReturn(TestKakaoBookSearchResponse.call());

        // when
        BookSearchResponse response = bookSearchService.searchBook("byrage", "테스트", 1, 1);

        // then
        then(kakaoBookSearchClient).should().inquireKakaoBookSearch(anyString(), anyInt(), anyInt());
        then(naverBookSearchClient).should(never()).inquireNaverBookSearch(anyString(), anyInt(), anyInt());
        assertThat(response).satisfies(r ->
                assertThat(((TestKakaoBookSearchResponse) r.getData()).isCalled()).isTrue());

    }

    @Test
    @DisplayName("카카오 API 예외 발생시 네이버 API를 호출한다")
    void inquireNaverApiWhenKakaoApiOccurredException() {
        // given
        given(kakaoBookSearchClient.inquireKakaoBookSearch(anyString(), anyInt(), anyInt()))
                .willThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        given(naverBookSearchClient.inquireNaverBookSearch(anyString(), anyInt(), anyInt()))
                .willReturn(TestNaverBookSearchResponse.call());

        // when
        BookSearchResponse response = bookSearchService.searchBook("byrage", "테스트", 1, 1);

        // then
        then(kakaoBookSearchClient).should().inquireKakaoBookSearch(anyString(), anyInt(), anyInt());
        then(naverBookSearchClient).should().inquireNaverBookSearch(anyString(), anyInt(), anyInt());
        assertThat(response).satisfies(r ->
                assertThat(((TestNaverBookSearchResponse) r.getData()).isCalled()).isTrue());

    }

    public static class TestKakaoBookSearchResponse extends KakaoBookSearchResponse {

        private boolean isCalled;

        public boolean isCalled() {
            return isCalled;
        }

        public static TestKakaoBookSearchResponse call() {
            TestKakaoBookSearchResponse response = new TestKakaoBookSearchResponse();
            response.isCalled = true;
            return response;
        }
    }

    public static class TestNaverBookSearchResponse extends NaverBookSearchResponse {

        private boolean isCalled;

        public boolean isCalled() {
            return isCalled;
        }

        public static NaverBookSearchResponse call() {
            TestNaverBookSearchResponse response = new TestNaverBookSearchResponse();
            response.isCalled = true;
            return response;
        }
    }
}