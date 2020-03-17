package com.github.byrage.springbootbooksearch.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book/search")
public class BookSearchController {

    @GetMapping
    public void searchBookByKeyword(@RequestParam String keyword) {
        log.info("searchBookByKeyword. keyword={}", keyword);
    }
}
