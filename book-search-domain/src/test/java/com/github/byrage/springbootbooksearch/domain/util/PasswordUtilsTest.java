package com.github.byrage.springbootbooksearch.domain.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilsTest {

    @MethodSource
    @ParameterizedTest(name = "[{index}] isEqualsEncodedValue 테스트. rawValue={0}, encodedValue={1}, expected={2}")
    void isEqualsEncodedValue(String rawValue, String encodedValue, boolean expected) {

        assertThat(PasswordUtils.isEqualsEncodedValue(rawValue, encodedValue)).isEqualTo(expected);
    }

    private static Stream<Arguments> isEqualsEncodedValue() {

        return Stream.of(
                Arguments.of("test-input", "dGVzdC1pbnB1dA==", true),
                Arguments.of("dGVzdC1pbnB1dA==", "test-input", false),
                Arguments.of("value", "value", "false"),
                Arguments.of("", "", true),
                Arguments.of(null, null, true)
        );
    }

    @MethodSource
    @ParameterizedTest(name = "[{index}] encode 테스트. input={0}, expected={1}")
    void encode(String input, String expected) {
        assertThat(PasswordUtils.encode(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> encode() {

        return Stream.of(
                Arguments.of("test-input", "dGVzdC1pbnB1dA=="),
                Arguments.of("", ""),
                Arguments.of(null, null)
        );
    }
}