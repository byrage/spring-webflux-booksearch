package com.github.byrage.springbootbooksearch.api.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordRequest {
    String password;
}
