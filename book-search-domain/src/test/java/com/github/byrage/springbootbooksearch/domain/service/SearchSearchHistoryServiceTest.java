package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
    @DisplayName("[{index}] 히스토리 조회 시 검색일시 내림차순으로 정렬한다")
    void findHistoriesByMemberId() {
        // given
        String memberId = "byrage";
        Member member = memberRepository.save(Member.builder().memberId(memberId).build());
        LocalDateTime localDateTime = LocalDateTime.of(2020, 3, 18, 0, 0);
        List<SearchHistory> histories = Arrays.asList(
                buildHistory(member.getId(), "키워드1", localDateTime.minusHours(2)),
                buildHistory(member.getId(), "키워드2", localDateTime.minusHours(1)),
                buildHistory(member.getId(), "키워드3", localDateTime)
        );
        searchHistoryRepository.saveAll(histories);

        // when
        List<SearchHistory> result = searchHistoryService.findHistoriesByMemberId(memberId);

        // then
        assertThat(result).hasSize(histories.size())
                .extracting(SearchHistory::getSearchDate)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    private SearchHistory buildHistory(Long memberId, String searchKeyword, LocalDateTime searchDateTime) {
        return SearchHistory.builder()
                .member(Member.builder()
                        .id(memberId)
                        .build())
                .searchKeyword(searchKeyword)
                .searchDateTime(searchDateTime)
                .build();
    }
}