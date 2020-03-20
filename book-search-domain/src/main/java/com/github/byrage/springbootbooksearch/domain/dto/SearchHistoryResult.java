package com.github.byrage.springbootbooksearch.domain.dto;

import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHistoryResult {

    private String searchKeyword;
    private LocalDateTime searchDate;

    private SearchHistoryResult(String searchKeyword, LocalDateTime searchDate) {
        this.searchKeyword = searchKeyword;
        this.searchDate = searchDate;
    }

    public static SearchHistoryResult of(SearchHistory searchHistory) {
        SearchHistoryResult result = new SearchHistoryResult();
        result.searchKeyword = searchHistory.getSearchKeyword();
        result.searchDate = searchHistory.getSearchDate();
        return result;
    }
}
