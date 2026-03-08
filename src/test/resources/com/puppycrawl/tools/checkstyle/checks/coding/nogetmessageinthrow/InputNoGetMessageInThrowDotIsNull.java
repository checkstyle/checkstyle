/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInThrowDotIsNull {

    void test() {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            // getMessage() without an object → METHOD_CALL has NO DOT
            throw new RuntimeException(getMessage());
        }
    }

    // local helper method
    private String getMessage() {
        return "local message";
    }
}
// xdoc section -- end
