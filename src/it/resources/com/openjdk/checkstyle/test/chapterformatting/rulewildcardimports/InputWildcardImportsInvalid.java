package com.openjdk.checkstyle.test.chapterformatting.rulewildcardimports;

// violation first line 'Header is missing*'

import java.util.*;

import static java.lang.Math.*; // violation, 'Only '1' star import is allowed per file.'

public class InputWildcardImportsInvalid {

    private final List<String> items = List.of("one", "two");

    public int maxValue(int first, int second) {
        return max(first, second) + items.size();
    }
}
