package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.exception.NotExistMemberException;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<SearchHistory> findHistoriesByMemberId(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(NotExistMemberException::new);
        return searchHistoryRepository.findAllByMemberIdOrderBySearchDateDesc(member.getId());
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
