/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInThrowNested {

    void method1() {
        // Nested try-catch with violation in inner try
        try {
            throw new IOException();
        } catch (IOException ex) {
            try {
                throw new IOException("Error: " + ex.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
            } catch (RuntimeException re) {
                System.out.println(re);
            }
        }

        // Nested try-catch with violation in inner catch
        try {
            try {
                throw new IOException();
            } catch (IOException inner) {
                throw new IOException("Inner: " + inner.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
            }
        } catch (RuntimeException outer) {
            System.out.println(outer);
        }
    }
}
// xdoc section -- end
