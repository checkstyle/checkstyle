/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInCatchThrowDefault {

    void method1() throws IOException {
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage(), ex);
                // violation above 'Avoid using 'ex.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
        }
    }

    void method2() throws IOException {
        try {
            throw new IOException();
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
                // violation above 'Avoid using 'exception.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
        }
    }

    void method3() throws IOException {
        try {
            throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Prefix " + e.getMessage() + " suffix", e);
                // violation above 'Avoid using 'e.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
        }
    }
}
// xdoc section -- end
