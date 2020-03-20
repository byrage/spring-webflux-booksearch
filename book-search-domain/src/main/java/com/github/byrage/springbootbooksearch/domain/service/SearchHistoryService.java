package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.exception.NotExistMemberException;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import com.github.byrage.springbootbooksearch.domain.util.PageRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<SearchHistory> findHistoriesByMemberId(String memberId, Integer page, Integer size) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(NotExistMemberException::new);
        return searchHistoryRepository.findAllByMemberIdOrderBySearchDateDesc(member.getId(), PageRequestBuilder.build(page, size));
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
}
