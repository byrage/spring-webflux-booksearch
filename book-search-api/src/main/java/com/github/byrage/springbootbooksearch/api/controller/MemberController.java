package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.PasswordRequest;
import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import com.github.byrage.springbootbooksearch.domain.service.MemberService;
import com.github.byrage.springbootbooksearch.domain.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Member signUp(@PathVariable String id, @RequestBody PasswordRequest password) {
        log.info("signUp. id={}, password={}", id, password);
        return memberService.signUp(id, password.getPassword());
    }

    @GetMapping("/{id}/book/search/history")
    public List<SearchHistory> findHistories(@PathVariable String id, @RequestParam String targetDate) {
        log.info("findHistories. id={}, targetDate={}", id, targetDate);
        return searchHistoryService.findHistoriesByMemberId(id);
    }

}
