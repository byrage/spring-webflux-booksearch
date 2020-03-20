package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PopulateKeywordServiceTest {

    @Autowired
    private PopulateKeywordService populateKeywordService;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    @DisplayName("인기 검색어 조회")
    void findPopulateKeywords() {
        // given
        searchHistoryRepository.saveAll(dummySearchHistoriesForTestPopulateKeywords());

        // when
        List<PopulateSearchKeyword> result = populateKeywordService.findPopulateKeywords();

        // then
        assertThat(result).hasSizeLessThanOrEqualTo(PopulateKeywordService.POPULATE_SEARCH_KEYWORDS_SIZE);
        assertThat(result).first().extracting(PopulateSearchKeyword::getSearchKeyword).isEqualTo("카카오뱅크");
        assertThat(result).extracting(PopulateSearchKeyword::getCount).isSortedAccordingTo(Comparator.reverseOrder());
    }

    private List<SearchHistory> dummySearchHistoriesForTestPopulateKeywords() {
        String memberId = "byrage";
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

    private static SearchHistory buildHistory(String memberId, String searchKeyword, LocalDateTime searchDateTime) {
        return SearchHistory.builder()
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .searchKeyword(searchKeyword)
                .searchDateTime(searchDateTime)
                .build();
    }
}