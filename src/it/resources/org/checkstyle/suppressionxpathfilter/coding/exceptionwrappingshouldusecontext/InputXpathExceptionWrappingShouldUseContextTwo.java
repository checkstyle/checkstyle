package org.checkstyle.suppressionxpathfilter.coding.exceptionwrappingshouldusecontext;

public class InputXpathExceptionWrappingShouldUseContextTwo {

    public InputXpathExceptionWrappingShouldUseContextTwo(int value) {
        try {
            int x = 1 / 0;
        }
        catch (Exception ex) {
            throw new RuntimeException("error", ex); // warn
        }
    }
}
