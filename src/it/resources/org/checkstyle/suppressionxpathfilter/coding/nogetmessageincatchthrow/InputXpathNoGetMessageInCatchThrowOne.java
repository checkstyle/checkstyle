package org.checkstyle.suppressionxpathfilter.coding.nogetmessageincatchthrow;

public class InputXpathNoGetMessageInCatchThrowOne {
        void method() {
            try {
                // code
            } catch (Exception ex) {
                throw new RuntimeException("Error: " + ex.getMessage(), ex); // warn
            }
        }
}
