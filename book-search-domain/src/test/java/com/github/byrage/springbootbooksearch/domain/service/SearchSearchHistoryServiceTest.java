package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SearchSearchHistoryServiceTest {

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    private static final Long memberId = 99L;

    @MethodSource
    @ParameterizedTest(name = "[{index}] 히스토리 조회 시 검색일시 내림차순으로 정렬한다")
    void findHistoriesByMemberId(List<SearchHistory> histories) {
        // given
        searchHistoryRepository.saveAll(histories);

        // when
        List<SearchHistory> result = searchHistoryService.findHistoriesByMemberId(memberId);

        // then
        assertThat(result).hasSize(histories.size())
                .extracting(SearchHistory::getSearchDate)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    private static Stream<Arguments> findHistoriesByMemberId() {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 3, 18, 0, 0);

        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                buildHistory(memberId, "키워드1", localDateTime),
                                buildHistory(memberId, "키워드2", localDateTime.minusHours(1)),
                                buildHistory(memberId, "키워드3", localDateTime.minusHours(2))
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                buildHistory(memberId, "키워드3", localDateTime.minusHours(2)),
                                buildHistory(memberId, "키워드2", localDateTime.minusHours(1)),
                                buildHistory(memberId, "키워드1", localDateTime)
                        )
                )
        );
    }

    private static SearchHistory buildHistory(Long memberId, String searchKeyword, LocalDateTime searchDateTime) {
        return SearchHistory.builder()
                .member(Member.builder()
                        .id(memberId)
                        .build())
                .searchKeyword(searchKeyword)
                .searchDateTime(searchDateTime)
                .build();
    }
}