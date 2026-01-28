/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;
import java.sql.SQLException;

// xdoc section -- start
class InputNoGetMessageInThrowMultipleCatches {

    void method1() throws IOException {
        // Multiple catch blocks
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("IO: " + ex.getMessage(), ex);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        } catch (RuntimeException ex) {
            // Not a violation - different catch block
            System.out.println(ex);
        }
    }

    void method2() throws Exception {
        // Multiple catch blocks with multi-catch
        try {
            if (Math.random() > 0.5) {
                throw new IOException();
            }
            throw new SQLException();
        } catch (IOException | SQLException ex) {
            throw new RuntimeException("Error: " + ex.getMessage(), ex);
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }
}
// xdoc section -- end
