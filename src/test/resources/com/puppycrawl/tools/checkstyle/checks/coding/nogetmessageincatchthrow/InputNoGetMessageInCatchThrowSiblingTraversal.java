/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

class InputNoGetMessageInCatchThrowSiblingTraversal {
    void method() throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            int x = 0;
            String s = "test";
            throw new RuntimeException(ex.getMessage(), ex);
            // violation above 'Avoid using 'ex.getMessage()'
            // in throw statement within catch block. The exception message
            // is already available through the cause chain.'
        }
    }
}
