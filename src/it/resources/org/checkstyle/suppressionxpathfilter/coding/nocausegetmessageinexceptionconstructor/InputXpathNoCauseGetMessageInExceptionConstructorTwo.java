/*
NoCauseGetMessageInExceptionConstructor


*/

package org.checkstyle.suppressionxpathfilter.coding.nocausegetmessageinexceptionconstructor;

import java.io.IOException;

public class InputXpathNoCauseGetMessageInExceptionConstructorTwo {
    void test() throws IOException {
        try {
            throw new IOException("test");
        } catch (IOException exception) {
            // violation below "Avoid using '.getMessage()' in throw statement."
            throw new IOException(exception.getMessage(), exception);
        }
    }
}
