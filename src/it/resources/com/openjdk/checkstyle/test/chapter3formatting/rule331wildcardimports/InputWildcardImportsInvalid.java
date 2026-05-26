package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.util.*; // violation '.*import should be avoided - java\.util\.\*\.'

import static java.lang.Math.*; // violation '.*import should be avoided - java\.lang\.Math\.\*\.'

public class InputWildcardImportsInvalid {

    private final List<String> items = List.of("one", "two");

    public int maxValue(int first, int second) {
        return max(first, second) + items.size();
    }
}
