package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.dto.SearchHistoryResult;
import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SearchSearchHistoryServiceTest {

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("히스토리 조회 시 검색일시 내림차순으로 정렬한다")
    void findHistoriesByMemberId() {
        // given
        int size = 25;
        String memberId = "byrage";
        Member member = memberRepository.save(Member.builder().memberId(memberId).build());
        LocalDateTime localDateTime = LocalDateTime.of(2020, 3, 18, 0, 0);
        List<SearchHistory> histories = IntStream.rangeClosed(1, 100)
                .mapToObj(minusHour -> buildHistory(member, "키워드", localDateTime.minusHours(minusHour)))
                .collect(Collectors.toList());
        Collections.shuffle(histories);
        searchHistoryRepository.saveAll(histories);

        // when
        Page<SearchHistoryResult> result = searchHistoryService.findHistoriesByMemberId(memberId, 0, size);

        // then
        assertThat(result).hasSize(size)
                .extracting(SearchHistoryResult::getSearchDate)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    private SearchHistory buildHistory(Member member, String searchKeyword, LocalDateTime searchDateTime) {
        return SearchHistory.builder()
                .member(member)
                .searchKeyword(searchKeyword)
                .searchDateTime(searchDateTime)
                .build();
    }
}