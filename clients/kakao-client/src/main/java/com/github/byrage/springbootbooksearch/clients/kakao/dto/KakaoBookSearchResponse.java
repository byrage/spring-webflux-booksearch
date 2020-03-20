package com.github.byrage.springbootbooksearch.clients.kakao.dto;

import com.github.byrage.springbootbooksearch.clients.core.dto.BookSearchResponse;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoBookSearchResponse implements BookSearchResponse {

    private Meta meta;
    private List<Document> documents;
}
