package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

// violation first line 'Header mismatch'

public class InputWrappingExpressionsValid {

    void example() {
        String s = "Hello"
                + "World";

        if (10
                == 20) {
        }

        int c = 10
                / 5;

        int b
                = 10;
        int e
                = 10;
        b
                += 10;
        c
                *= 10;
        c
                -= 5;
        c -= 5;
        c
                /= 2;
        c
                %= 1;
        c
                >>= 1;
        c
                >>>= 1;
        c
                &= 1;
        c
                <<= 1;
    }
}
