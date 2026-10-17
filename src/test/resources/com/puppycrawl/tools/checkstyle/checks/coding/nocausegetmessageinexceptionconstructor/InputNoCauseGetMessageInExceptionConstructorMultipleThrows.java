/*
NoCauseGetMessageInExceptionConstructor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nocausegetmessageinexceptionconstructor;

import java.io.IOException;

class InputNoCauseGetMessageInExceptionConstructorMultipleThrows {
    void method(boolean flag) throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            if (flag) {
                throw new IOException(ex.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
            }
            else {
                throw new RuntimeException("other", ex);
            }
        }
    }
}
