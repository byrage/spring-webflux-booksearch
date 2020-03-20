package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.service.PopulateKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/popular-keyword")
@RequiredArgsConstructor
public class PopularKeywordController {

    private final PopulateKeywordService populateKeywordService;

    @GetMapping
    public List<PopulateSearchKeyword> findPopularKeyword() {
        log.info("findPopularKeyword");
        return populateKeywordService.findPopulateKeywords();
    }
}
