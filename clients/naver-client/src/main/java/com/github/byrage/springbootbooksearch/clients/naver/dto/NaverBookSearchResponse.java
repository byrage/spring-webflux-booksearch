package com.github.byrage.springbootbooksearch.clients.naver.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverBookSearchResponse {

    private String lastBuildDate;
    private Long total;
    private Long start;
    private Long display;
    private List<Item> items;
}
