package org.checkstyle.suppressionxpathfilter.coding.nogetmessageinthrow;

import java.io.IOException;

public class InputXpathNoGetMessageInThrowTwo {
    void test() {
        try {
            throw new IOException("test");
        } catch (IOException exception) {
            throw new IOException(exception.getMessage(), exception); // warn
        }
    }
}
