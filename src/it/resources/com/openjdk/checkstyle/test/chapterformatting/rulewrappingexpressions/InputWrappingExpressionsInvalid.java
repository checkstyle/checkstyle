package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

// violation first line 'Header mismatch*'

import java.util.Arrays;

public class InputWrappingExpressionsInvalid {
    void test() {
        int x = 1 + // violation ''\+' should be on a new line.'
                2 - // violation ''-' should be on a new line.'
                3
                -
                4;
        x = x + 2;
        boolean y = true
                &&
                false;
        y = true && // violation ''&&' should be on a new line.'
                false;
        y = false
                && true;
        Arrays.sort(null, String
                    ::
                    compareToIgnoreCase);
        Arrays.sort(null, String:: // violation ''::' should be on a new line.'
                    compareToIgnoreCase);
        Arrays.sort(null, String
                    ::compareToIgnoreCase);
    }

    String typeGuardAfterParenthesizedTrueIfStatement2(Object p) {
        Object o = p;
        if (o != null && // violation ''&&' should be on a new line.'
                o instanceof Integer i && // violation ''&&' should be on a new line.'
                        i == 0) {
            return "true";
        } else if (o != null && // violation ''&&' should be on a new line.'
                o instanceof Integer i && // violation ''&&' should be on a new line.'
                        i == 2 && // violation ''&&' should be on a new line.'
                (o = i) != null) {
            return "second";
        } else {
            return "any";
        }
    }
}
