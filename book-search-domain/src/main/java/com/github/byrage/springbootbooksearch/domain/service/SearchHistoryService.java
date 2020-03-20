package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.dto.SearchHistoryResult;
import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.exception.NotExistMemberException;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import com.github.byrage.springbootbooksearch.domain.util.PageRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<SearchHistoryResult> findHistoriesByMemberId(String memberId, Integer page, Integer size) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(NotExistMemberException::new);
        Page<SearchHistory> searchHistories = searchHistoryRepository.findAllByMemberIdOrderBySearchDateDesc(member.getId(), PageRequestBuilder.build(page, size));
        return buildPageSearchHistoryResult(searchHistories);
    }

    @Transactional
    public SearchHistory saveSearchHistory(String memberId, String keyword, LocalDateTime now) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(NotExistMemberException::new);

        return searchHistoryRepository.save(SearchHistory.builder()
                .member(member)
                .searchKeyword(keyword)
                .searchDateTime(now)
                .build());
    }

    private PageImpl<SearchHistoryResult> buildPageSearchHistoryResult(Page<SearchHistory> searchHistories) {
        return new PageImpl<>(
                searchHistories.stream()
                        .map(SearchHistoryResult::of)
                        .collect(Collectors.toList()),
                searchHistories.getPageable(),
                searchHistories.getTotalElements()
        );
    }
}
