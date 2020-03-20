package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.CommonResponse;
import com.github.byrage.springbootbooksearch.api.dto.PasswordRequest;
import com.github.byrage.springbootbooksearch.domain.dto.SearchHistoryResult;
import com.github.byrage.springbootbooksearch.domain.service.MemberService;
import com.github.byrage.springbootbooksearch.domain.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/{id}")
    public boolean signIn(@PathVariable String id, @RequestBody PasswordRequest password) {
        log.info("signIn. id={}, password={}", id, password);
        return memberService.signIn(id, password.getPassword());
    }

    @PostMapping("/{id}")
    public boolean signUp(@PathVariable String id, @RequestBody PasswordRequest password) {
        log.info("signUp. id={}, password={}", id, password);
        return memberService.signUp(id, password.getPassword());
    }

    @GetMapping("/{id}/book/search/history")
    public CommonResponse<List<SearchHistoryResult>> findHistories(@PathVariable String id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("findHistories. id={}, page={}, size={}", id, page, size);
        Page<SearchHistoryResult> searchHistories = searchHistoryService.findHistoriesByMemberId(id, page, size);
        return CommonResponse.success(
                searchHistories.getTotalElements(),
                searchHistories.getNumber(),
                searchHistories.getContent());
    }

}
