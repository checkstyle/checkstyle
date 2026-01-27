package org.checkstyle.suppressionxpathfilter.coding.nogetmessageincatchthrow;

import java.io.IOException;

public class InputXpathNoGetMessageInCatchThrowTwo {
    void test() {
        try {
            throw new IOException("test");
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage(), exception); // warn
        }
    }
}
