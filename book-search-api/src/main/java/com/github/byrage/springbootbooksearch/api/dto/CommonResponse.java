package com.github.byrage.springbootbooksearch.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse <T> {

    private String status;
    private String message;
    private long totalCount;
    private long currentPage;
    private T data;
    private LocalDateTime serverDatetime;

    private CommonResponse(ServerStatus serverStatus, String message, long totalCount, long currentPage, T data) {
        this.status = serverStatus.getDefaultMessage();
        this.message = message;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> CommonResponse<T> success(long totalCount, long currentPage, T data) {
        return new CommonResponse<>(
                ServerStatus.SUCCESS,
                ServerStatus.SUCCESS.getDefaultMessage(),
                totalCount,
                currentPage,
                data);
    }

    public static CommonResponse<Void> fail() {
        return new CommonResponse<>(
                ServerStatus.FAIL,
                ServerStatus.FAIL.getDefaultMessage(),
                0,
                0,
                null);
    }

    public static CommonResponse<Void> fail(String message) {
        return new CommonResponse<>(
                ServerStatus.FAIL,
                message,
                0,
                0,
                null);
    }
}
