package org.checkstyle.suppressionxpathfilter.coding.exceptionthrowshouldusecontext;

public class InputXpathExceptionThrowShouldUseContextTwo {

    public InputXpathExceptionThrowShouldUseContextTwo(int value) {
        try {
            int x = 1 / 0;
        }
        catch (Exception ex) {
            throw new RuntimeException("error", ex); // warn
        }
    }
}
