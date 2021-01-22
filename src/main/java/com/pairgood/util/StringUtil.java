package com.pairgood.util;

public class StringUtil {

    public boolean matches(String value, String... patterns) {
        boolean answer = false;

        for (String pattern : patterns) {
            if (value.equals(pattern)) {
                answer = true;
            }
        }

        return answer;
    }

    public boolean notMatch(String value, String... patterns) {
        return !matches(value, patterns);
    }
}
