package com.github.byrage.springbootbooksearch.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerStatus {
    SUCCESS("성공"),
    FAIL("실패");

    private final String status;
}
