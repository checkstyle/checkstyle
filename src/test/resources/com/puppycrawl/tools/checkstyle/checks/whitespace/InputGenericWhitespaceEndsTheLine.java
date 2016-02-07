package com.puppycrawl.tools.checkstyle.checks.whitespace;

public class InputGenericWhitespaceEndsTheLine {
    public boolean returnsGenericObjectAtEndOfLine(Object otherObject) {
        return otherObject instanceof Enum<?>;
    }
}