package com.github.byrage.springbootbooksearch.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageRequestBuilder {

    private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, 25);

    public static PageRequest build(Integer page, Integer size) {
        if (isInvalid(page, size)) {
            return DEFAULT_PAGE_REQUEST;
        }
        return PageRequest.of(page, size);
    }

    private static boolean isInvalid(Integer page, Integer size) {
        return Objects.isNull(page) || Objects.isNull(size);
    }
}
