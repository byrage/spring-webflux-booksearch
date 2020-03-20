package com.github.byrage.springbootbooksearch.clients.naver;

import com.github.byrage.springbootbooksearch.clients.naver.dto.NaverBookSearchResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration-test")
@SpringBootTest
@ActiveProfiles("test")
class NaverBookSearchClientTest {

    @Autowired
    private NaverBookSearchClient naverBookSearchClient;

    @Test
    void inquireNaverBookSearch() {
        ResponseEntity<NaverBookSearchResponse> response = naverBookSearchClient.inquireNaverBookSearch("네이버", 1, 10);

        assertThat(response).isNotNull()
                .satisfies(responseEntity -> {
                    assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
                    assertThat(responseEntity.getBody()).isNotNull()
                            .extracting(NaverBookSearchResponse::getItems).asList().hasSizeGreaterThan(0);
                });
    }
}