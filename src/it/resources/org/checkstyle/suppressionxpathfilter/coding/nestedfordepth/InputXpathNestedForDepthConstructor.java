package org.checkstyle.suppressionxpathfilter.coding.nestedfordepth;

public class InputXpathNestedForDepthConstructor {
    InputXpathNestedForDepthConstructor() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) { // warn
                }
            }
        }
    }
}
