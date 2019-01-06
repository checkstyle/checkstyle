package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.Map;
import java.util.stream.IntStream;

public class InputNoWhitespaceAfterArrayDeclarator {
    @SuppressWarnings("unchecked")
    Map.Entry<Integer,String>[] genEntries(int n) {
        return IntStream.range(0, n)
            .mapToObj(i -> entry(i, valueFor(i)))
            .toArray(Map.Entry []::new);
    }

    static Map.Entry<Integer, String> entry(int k, String v) {
        return null;
    }

    static String valueFor(int i) {
        return "abcdefghijklmnopqrst".substring(i,i+1);
    }
}
