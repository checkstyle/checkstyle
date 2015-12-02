package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputGenericWhitespaceEndsTheLine {
    public boolean returnsGenericObjectAtEndOfLine(Object otherObject) {
        return otherObject instanceof Enum<?>;
    }
}