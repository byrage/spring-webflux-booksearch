package com.github.byrage.springbootbooksearch.clients.naver.dto;

import com.github.byrage.springbootbooksearch.clients.core.dto.BookSearchResponse;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverBookSearchResponse implements BookSearchResponse {

    private String lastBuildDate;
    private Long total;
    private Long start;
    private Long display;
    private List<Item> items;
}
