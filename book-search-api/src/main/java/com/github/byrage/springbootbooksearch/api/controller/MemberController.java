package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.PasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/{id}")
    public void signIn(@PathVariable String id, @RequestBody PasswordRequest password) {
        log.info("signIn. id={}, password={}", id, password);
    }

    @PostMapping("/{id}")
    public void signUp(@PathVariable String id, @RequestBody PasswordRequest password) {
        log.info("signUp. id={}, password={}", id, password);
    }

    @GetMapping("/{id}/book/search/history")
    public void findHistories(@PathVariable String id, @RequestParam String targetDate) {
        log.info("findHistories. id={}, targetDate={}", id, targetDate);
    }

}
