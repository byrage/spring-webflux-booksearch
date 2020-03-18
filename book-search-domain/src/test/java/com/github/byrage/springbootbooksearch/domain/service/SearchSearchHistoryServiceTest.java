package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

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

    @Test
    @DisplayName("인기 검색어 조회")
    void findPopulateKeywords() {
        // given
        searchHistoryRepository.saveAll(dummySearchHistoriesForTestPopulateKeywords());

        // when
        List<PopulateSearchKeyword> result = searchHistoryService.findPopulateKeywords();

        // then
        assertThat(result).hasSizeLessThanOrEqualTo(SearchHistoryService.POPULATE_SEARCH_KEYWORDS_SIZE);
        assertThat(result).first().extracting(PopulateSearchKeyword::getSearchKeyword).isEqualTo("카카오뱅크");
        assertThat(result).extracting(PopulateSearchKeyword::getCount).isSortedAccordingTo(Comparator.reverseOrder());
    }

    private List<SearchHistory> dummySearchHistoriesForTestPopulateKeywords() {
        long memberId = 1L;
        LocalDateTime localDateTime = LocalDateTime.of(2020, 3, 19, 0, 0);

        return Stream.of(
                IntStream.rangeClosed(1, 100)
                        .mapToObj(i -> buildHistory(memberId, "카카오뱅크", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 10)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드10", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 20)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드20", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 80)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드80", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 70)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드70", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 30)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드30", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 60)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드60", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 90)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드90", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 25)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드25", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 3)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드3", localDateTime))
                        .collect(Collectors.toList()),
                IntStream.rangeClosed(1, 2)
                        .mapToObj(i -> buildHistory(memberId, "검색키워드2", localDateTime))
                        .collect(Collectors.toList())
        )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static SearchHistory buildHistory(Long memberId, String searchKeyword, LocalDateTime searchDate) {
        return SearchHistory.builder().memberId(memberId).searchKeyword(searchKeyword).searchDate(searchDate).build();
    }
}