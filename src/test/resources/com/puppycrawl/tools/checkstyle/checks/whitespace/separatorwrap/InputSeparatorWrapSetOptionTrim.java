/*
SeparatorWrap
option = \tNL
tokens = METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

import java.util.Arrays;

public class InputSeparatorWrapSetOptionTrim {

    void Method() {
        String[] stringArray = { "Barbara", "James", "Mary", "John",
            "Patricia", "Linda" }; // violation below ''::' should be on a new line'
            Arrays.sort(stringArray, String::
                compareToIgnoreCase);
    }
}
