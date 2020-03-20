package com.github.byrage.springbootbooksearch.clients.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Meta {

    @JsonProperty("is_end")
    private boolean isEnd;
    @JsonProperty("pageable_count")
    private long pageableCount;
    @JsonProperty("total_count")
    private long totalCount;
}
