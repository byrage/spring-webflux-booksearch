package com.github.byrage.springbootbooksearch.clients.naver.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverBookSearchResponse {

    private String lastBuildDate;
    private long total;
    private long start;
    private long display;
    private List<Item> items;
}
