package com.openjdk.checkstyle.test.chapterformatting.rulewildcardimports;

// violation first line 'Header mismatch'

import java.util.*;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class InputWildcardImportsValid {

    private final List<String> items;

    public InputWildcardImportsValid(List<String> item) {
        this.items = item;
    }

    public Map<String, String> getItems() {
        return Map.of("size", String.valueOf(items.size()));
    }

    public int maxValue(int first, int second) {
        return max(first, second);
    }
}
