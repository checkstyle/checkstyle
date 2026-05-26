package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class InputWildcardImportsValid {

    private final List<String> items;

    public InputWildcardImportsValid(List<String> items) {
        this.items = items;
    }

    public Map<String, String> getItems() {
        return Map.of("size", String.valueOf(items.size()));
    }

    public int maxValue(int first, int second) {
        return max(first, second);
    }
}
