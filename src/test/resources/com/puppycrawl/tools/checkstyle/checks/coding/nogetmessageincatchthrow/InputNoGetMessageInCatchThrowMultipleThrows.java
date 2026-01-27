/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

class InputNoGetMessageInCatchThrowMultipleThrows {
    void method(boolean flag) throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            if (flag) {
                throw new RuntimeException(ex.getMessage(), ex);
                // violation above 'Avoid using 'ex.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
            }
            else {
                throw new RuntimeException("other", ex);
            }
        }
    }
}
