/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInThrowNested {

    void method1() {
        // Nested try-catch with violation in outer
        try {
            throw new IOException();
        } catch (IOException ex) {
            try {
                throw new RuntimeException("Error: " + ex.getMessage(), ex);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
            } catch (RuntimeException re) {
                System.out.println(re);
            }
        }

        // Nested try-catch with violation in inner
        try {
            try {
                throw new IOException();
            } catch (IOException inner) {
                throw new RuntimeException("Inner: " + inner.getMessage(), inner);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
            }
        } catch (RuntimeException outer) {
            System.out.println(outer);
        }
    }
}
// xdoc section -- end
