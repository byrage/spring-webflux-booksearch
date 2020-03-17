package com.github.byrage.springbootbooksearch.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PasswordUtils {

    public static boolean isEqualsEncodedValue(String rawValue, String encodedValue) {
        return ObjectUtils.nullSafeEquals(encode(rawValue), encodedValue);
    }

    public static String encode(String rawValue) {
        if (rawValue == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(rawValue.getBytes());
    }
}
