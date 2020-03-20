package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.repository.SearchHistoryRepository;
import com.github.byrage.springbootbooksearch.domain.util.PageRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PopulateKeywordService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional(readOnly = true)
    public Page<PopulateSearchKeyword> findPopulateKeywords(int page, int size) {
        return searchHistoryRepository.findPopulateSearchKeywords(PageRequestBuilder.build(page, size));
    }
}
