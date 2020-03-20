package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    static final int POPULATE_SEARCH_KEYWORDS_SIZE = 10;
    private static final PageRequest POPULATE_SEARCH_KEYWORDS_PAGE_REQUEST = PageRequest.of(0, POPULATE_SEARCH_KEYWORDS_SIZE);

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional(readOnly = true)
    public List<SearchHistory> findHistoriesByMemberId(Long memberId) {
        return searchHistoryRepository.findAllByMemberIdOrderBySearchDateDesc(memberId);
    }

    @Transactional(readOnly = true)
    public List<PopulateSearchKeyword> findPopulateKeywords() {
        return searchHistoryRepository.findPopulateSearchKeywords(POPULATE_SEARCH_KEYWORDS_PAGE_REQUEST);
    }
}
