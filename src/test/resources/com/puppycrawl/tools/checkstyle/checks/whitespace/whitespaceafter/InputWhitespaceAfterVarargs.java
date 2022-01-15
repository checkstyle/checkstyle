/*
WhitespaceAfter
tokens = ELLIPSIS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.util.List;

public class InputWhitespaceAfterVarargs {

    public void method(int...obj) { // violation
    }

    public InputWhitespaceAfterVarargs(String first, List<Integer>...second) { // violation
    }

    public <T> void anotherMethod(List<T>...args) { // violation
    }

    public static String multipleArguments(int l, String format, Object ... args) { // ok
        return format;
    }

    private void noWhitespaceBefore(boolean ...args) { // violation
    }

    testInterface<Integer> obj = (Integer... i) -> { // ok
    };
}

@FunctionalInterface
interface testInterface<T> {
    void method(T ...args); // violation
}
