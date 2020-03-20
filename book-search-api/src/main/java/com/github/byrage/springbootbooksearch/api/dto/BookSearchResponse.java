package com.github.byrage.springbootbooksearch.api.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookSearchResponse {
    private Object data;

    @Builder
    public BookSearchResponse(Object data) {
        this.data = data;
    }
}
