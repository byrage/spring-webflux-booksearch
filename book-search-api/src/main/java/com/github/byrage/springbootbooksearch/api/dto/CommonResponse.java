package com.github.byrage.springbootbooksearch.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse <T> {

    private String status;
    private String message;
    private T data;
    private LocalDateTime serverDatetime;

    private CommonResponse(ServerStatus serverStatus, T data) {
        this(serverStatus, null, data);
    }

    private CommonResponse(ServerStatus serverStatus, String message, T data) {
        this.status = serverStatus.getStatus();
        this.message = message;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(ServerStatus.SUCCESS, data);
    }

    public static <T> CommonResponse<T> fail() {
        return new CommonResponse<>(ServerStatus.FAIL, null);
    }

    public static <T> CommonResponse<T> fail(String message) {
        return new CommonResponse<>(ServerStatus.FAIL, message, null);
    }
}
