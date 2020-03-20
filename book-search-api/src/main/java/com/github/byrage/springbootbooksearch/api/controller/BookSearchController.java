package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.BookSearchResponse;
import com.github.byrage.springbootbooksearch.api.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book/search")
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService bookSearchService;

    @GetMapping
    public BookSearchResponse searchBookByKeyword(@RequestParam String memberId, @RequestParam String keyword) {
        log.info("searchBookByKeyword. keyword={}", keyword);
        return bookSearchService.searchBook(memberId, keyword, 1, 10);
    }
}
