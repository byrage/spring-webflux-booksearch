package com.github.byrage.springbootbooksearch.api.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookSearchResult {
    private long totalCount;
    private long currentPage;
    private Object data;

    @Builder
    public BookSearchResult(long totalCount, long currentPage, Object data) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.data = data;
    }
}
