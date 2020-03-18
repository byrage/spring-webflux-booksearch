package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> findHistoriesByMemberId(Long memberId) {
        return searchHistoryRepository.findAllByMemberIdOrderBySearchDateDesc(memberId);
    }
}
