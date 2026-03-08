package org.checkstyle.suppressionxpathfilter.coding.nogetmessageinthrow;

public class InputXpathNoGetMessageInThrowOne {
        void method() throws Exception {
            try {
                // code
            } catch (Exception ex) {
                throw new Exception("Error: " + ex.getMessage(), ex); // warn
            }
        }
}
