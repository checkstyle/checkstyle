/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

class InputNoGetMessageInThrowSiblingTraversal {
    void method() throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            int x = 0;
            String s = "test";
            throw new IOException(ex.getMessage());
            // violation above 'Avoid using '.getMessage()'
            // in throw statement.
        }
    }
}
