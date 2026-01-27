/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

class InputNoGetMessageInThrowMultipleThrows {
    void method(boolean flag) throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            if (flag) {
                throw new RuntimeException(ex.getMessage(), ex);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
            }
            else {
                throw new RuntimeException("other", ex);
            }
        }
    }
}
