package org.checkstyle.suppressionxpathfilter.coding.nogetmessageinthrow;

public class InputXpathNoGetMessageInThrowOne {
        void method() {
            try {
                // code
            } catch (Exception ex) {
                throw new Exception("Error: " + ex.getMessage(), ex); // warn
            }
        }
}
