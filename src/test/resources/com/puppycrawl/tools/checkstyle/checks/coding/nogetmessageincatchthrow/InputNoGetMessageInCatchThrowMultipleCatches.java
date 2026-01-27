/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;
import java.sql.SQLException;

// xdoc section -- start
class InputNoGetMessageInCatchThrowMultipleCatches {

    void method1() throws IOException {
        // Multiple catch blocks
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("IO: " + ex.getMessage(), ex);
                // violation above 'Avoid using 'ex.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
        } catch (RuntimeException ex) {
            // Not a violation - different catch block
            System.out.println(ex);
        }
    }

    void method2() throws IOException, SQLException {
        // Multiple catch blocks with multi-catch
        try {
            throw new IOException();
        } catch (IOException | SQLException ex) {
            throw new RuntimeException("Error: " + ex.getMessage(), ex);
                // violation above 'Avoid using 'ex.getMessage()'
                // in throw statement within catch block. The exception message
                // is already available through the cause chain.'
        }
    }
}
// xdoc section -- end
