package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

import java.util.Arrays;

public class InputSeparatorWrapForTestMethodRef {

    void goodCase() {
        String[] stringArray = { "Barbara", "James", "Mary", "John",
            "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String
                ::compareToIgnoreCase);
    }

    void badCase() {
        String[] stringArray = { "Barbara", "James", "Mary", "John",
            "Patricia", "Robert", "Michael", "Linda" };
        /* violation */ Arrays.sort(stringArray, String::
                compareToIgnoreCase);
    }
}
