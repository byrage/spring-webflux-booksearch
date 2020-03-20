package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.BookSearchResult;
import com.github.byrage.springbootbooksearch.api.dto.CommonResponse;
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
    public CommonResponse<Object> searchBookByKeyword(@RequestParam String member, @RequestParam String keyword) {
        log.info("searchBookByKeyword. member={}, keyword={}", member, keyword);
        BookSearchResult bookSearchResult = bookSearchService.searchBook(member, keyword, 1, 10);
        return CommonResponse.success(
                bookSearchResult.getTotalCount(),
                bookSearchResult.getCurrentPage(),
                bookSearchResult.getData());
    }
}
