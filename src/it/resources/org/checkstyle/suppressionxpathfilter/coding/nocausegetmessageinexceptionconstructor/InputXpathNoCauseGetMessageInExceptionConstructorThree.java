/*
NoCauseGetMessageInExceptionConstructor


*/

package org.checkstyle.suppressionxpathfilter.coding.nocausegetmessageinexceptionconstructor;

import java.io.IOException;

public class InputXpathNoCauseGetMessageInExceptionConstructorThree {
    void process() throws IOException {
        try {
            throw new IOException("sample");
        } catch (IOException exception) {
            // violation below "Avoid using '.getMessage()' in throw statement."
            throw new IOException("IO failed: " + exception.getMessage(), exception);
        }
    }
}

