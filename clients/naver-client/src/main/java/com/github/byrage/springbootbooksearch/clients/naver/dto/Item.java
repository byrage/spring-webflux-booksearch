package com.github.byrage.springbootbooksearch.clients.naver.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {

    private String title;
    private String link;
    private String image;
    private String author;
    private Long price;
    private Long discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
}
