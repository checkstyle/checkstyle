/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

public class InputGenericWhitespaceEndsTheLine { // ok
    public boolean returnsGenericObjectAtEndOfLine(Object otherObject) {
        return otherObject instanceof Enum<?>;
    }
}
