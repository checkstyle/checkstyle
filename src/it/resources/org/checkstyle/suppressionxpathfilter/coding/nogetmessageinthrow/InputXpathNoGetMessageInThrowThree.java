package org.checkstyle.suppressionxpathfilter.coding.nogetmessageinthrow;

import java.io.IOException;

public class InputXpathNoGetMessageInThrowThree {
    void process() throws IOException {
        try {
            throw new IOException("sample");
        } catch (IOException exception) {
            throw new IOException("IO failed: " + exception.getMessage(), exception); // warn
        }
    }
}

