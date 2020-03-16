package com.github.byrage.springwebfluxbooksearch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ContextLoadTest {

    @Test
    @DisplayName("스프링 컨텍스트 테스트")
    void contextLoad() {
        assertThat(true);
    }
}