/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck
option = space


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputSuppressWarningsHolder6 {
    public static void foo1(Object str) {
        String myString = (@SuppressWarnings("TypecastParenPad")
                String) str; // violation '')' is not preceded with whitespace'
    }

    @Target(ElementType.TYPE_USE)
    @interface SuppressWarnings {
        String value();
    }
}
