package com.github.byrage.springbootbooksearch.clients.kakao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Document {

    private List<String> authors;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", timezone = "Asia/Seoul")
    private LocalDateTime datetime;
    private String isbn;
    private Long price;
    private String publisher;
    @JsonProperty("sale_price")
    private Long salePrice;
    private String status;
    private String thumbnail;
    private String title;
    private List<String> translators;
    private String url;
}
