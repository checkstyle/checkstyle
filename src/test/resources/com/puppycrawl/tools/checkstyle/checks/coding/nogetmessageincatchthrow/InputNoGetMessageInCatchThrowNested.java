/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInCatchThrowNested {

    void method1() {
        // Nested try-catch with violation in outer
        try {
            throw new IOException();
        } catch (IOException ex) {
            try {
                throw new RuntimeException("Error: " + ex.getMessage(), ex);
                // violation above 'Avoid using 'ex.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
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
                // violation above 'Avoid using 'inner.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
            }
        } catch (RuntimeException outer) {
            System.out.println(outer);
        }
    }
}
// xdoc section -- end
