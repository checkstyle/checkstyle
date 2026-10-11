/*
WhitespaceAfter
tokens = ELLIPSIS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.util.List;

public class InputWhitespaceAfterVarargs {

    public void method(int...obj) { // violation ''...' is not followed by whitespace'
    }

    public InputWhitespaceAfterVarargs(String first,
           List<Integer>...second) { // violation ''...' is not followed by whitespace'
    }

    public <T> void anoMeth(List<T>...args) { // violation ''...' is not followed by whitespace'
    }

    public static String multipleArguments(int l, String format, Object ... args) {
        return format;
    }

    private void noWtSpcBefore(boolean ...args) { // violation ''...' is not followed by whitespace'
    }

    testInterface<Integer> obj = (Integer... i) -> {
    };
}

@FunctionalInterface
interface testInterface<T> {
    void method(T ...args); // violation ''...' is not followed by whitespace'
}
