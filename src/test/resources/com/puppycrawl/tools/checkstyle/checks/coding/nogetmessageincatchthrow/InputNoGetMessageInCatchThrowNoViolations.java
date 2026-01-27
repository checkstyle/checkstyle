/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInCatchThrowNoViolations {

    void method1() {
        // Catch with no throw statement at all
        try {
            throw new IOException();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        // Catch with throw but no getMessage
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error", ex);
        }

        // Empty catch block
        try {
            throw new IOException();
        } catch (IOException ex) {
            // empty
        }

        // Catch with only variable assignment
        try {
            throw new IOException();
        } catch (IOException ex) {
            String msg = "error";
        }

        // Catch with method call but not getMessage
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException(ex.toString(), ex);
        }

        // Catch with getMessage but not in throw
        try {
            throw new IOException();
        } catch (IOException ex) {
            String msg = ex.getMessage();
            System.out.println(msg);
            throw new RuntimeException("Error");
        }
    }
}
