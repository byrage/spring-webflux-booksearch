package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.CommonResponse;
import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.service.PopulateKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/popular-keyword")
@RequiredArgsConstructor
public class PopularKeywordController {

    private static final int POPULATE_SEARCH_PAGE = 0;
    private static final int POPULATE_SEARCH_SIZE = 10;
    private final PopulateKeywordService populateKeywordService;

    @GetMapping
    public CommonResponse<List<PopulateSearchKeyword>> findPopularKeyword() {
        log.info("findPopularKeyword");
        Page<PopulateSearchKeyword> populateKeywords = populateKeywordService.findPopulateKeywords(POPULATE_SEARCH_PAGE, POPULATE_SEARCH_SIZE);
        return CommonResponse.success(populateKeywords.getTotalElements(), populateKeywords.getNumber(), populateKeywords.getContent());
    }
}
