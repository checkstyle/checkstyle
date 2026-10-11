/*
ExceptionThrowShouldUseContext


*/

package org.checkstyle.suppressionxpathfilter.coding.exceptionthrowshouldusecontext;

public class InputXpathExceptionThrowShouldUseContextOne {

    void test(String param) {
        try {
            int x = 1 / 0;
        }
        catch (Exception ex) {
            throw new RuntimeException("error", ex); // violation 'Exception throw should always use context variables.'
        }
    }
}
