package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

/** Test cases for comma wrapping. */
public final class InputSeparatorWrap {

    /** Tests correct comma wrapping. */
    void goodCase() {
        foo(
            "first",
            "second",
            "third");
    }

    /** Tests incorrect comma wrapping. */
    void badCase() {
        foo(
            "first",
            "second"
            , "third"); // violation '',' is preceded with whitespace.'
                 // violation '',' should be on the previous line.'
    }

    private static void foo(final String... values) {
    }

}
