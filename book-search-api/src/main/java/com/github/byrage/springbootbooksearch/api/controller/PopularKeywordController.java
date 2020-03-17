package com.github.byrage.springbootbooksearch.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/popular-keyword")
public class PopularKeywordController {

    @GetMapping
    public void findPopularKeyword() {
        log.info("findPopularKeyword");
    }
}
