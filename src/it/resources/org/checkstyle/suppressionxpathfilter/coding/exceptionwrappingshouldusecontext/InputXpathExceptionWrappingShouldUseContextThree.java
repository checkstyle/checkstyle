package org.checkstyle.suppressionxpathfilter.coding.exceptionwrappingshouldusecontext;

public class InputXpathExceptionWrappingShouldUseContextThree {

    class Inner {
        void test(String param) {
            try {
                int x = 1 / 0;
            }
            catch (Exception ex) {
                throw new RuntimeException("error", ex); // warn
            }
        }
    }
}
