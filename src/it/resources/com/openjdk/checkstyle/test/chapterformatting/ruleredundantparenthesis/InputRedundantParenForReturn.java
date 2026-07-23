package com.openjdk.checkstyle.test.chapterformatting.ruleredundantparenthesis;

// violation first line 'Header mismatch'

import java.util.List;

/**
 * Test input for the redundant parenthesis rule for return statement.
 */
public final class InputRedundantParenForReturn {

    public int test() {
        String val = "1";
        return (val.equals("1") ? 1 : 0); // violation 'Unnecessary parentheses*'
    }

    public List<Integer> test1(List<Integer> list) {

        return (list  // violation 'Unnecessary parentheses*'
                .stream()
                .filter(num -> num % 2 == 0)
                .toList());
    }

    public int test2() {
        return ((10 * 4) + 5); // violation 'Unnecessary parentheses*'
    }

    public int test3() {
        return 1 + 2;
    }

}
