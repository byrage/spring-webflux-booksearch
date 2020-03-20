package com.github.byrage.springbootbooksearch.clients.kakao.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoBookSearchResponse {

    private Meta meta;
    private List<Document> documents;
}
