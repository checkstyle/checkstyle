/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInThrowDefault {

    void method1() throws IOException {
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage(), ex);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }

    void method2() throws IOException {
        try {
            throw new IOException();
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }

    void method3() throws IOException {
        try {
            throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Prefix " + e.getMessage() + " suffix", e);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }
}
// xdoc section -- end
