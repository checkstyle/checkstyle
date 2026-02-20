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
            throw new IOException("IO: " + ex.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        } catch (RuntimeException ex) {
            // ok, different catch block
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
            throw new IOException("Error: " + ex.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }

    // Sibling catches, same var name, different types. After the first catch
    // exits, its scope must be popped so that the second throw's 'ex' is only
    // resolved to the second catch (RuntimeException), not to the first.
    void method3(boolean flag) throws IOException {
        try {
            if (flag) {
                throw new IOException();
            }
        } catch (IOException ex) {
            throw new IOException("io: " + ex.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
        try {
            if (flag) {
                throw new RuntimeException();
            }
        } catch (RuntimeException ex) {
            // ok - 'ex' is RuntimeException here, not IOException
            throw new IOException("wrap: " + ex.getMessage());
        }
    }
}
// xdoc section -- end
