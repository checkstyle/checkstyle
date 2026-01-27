/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

class InputNoGetMessageInCatchThrowCorrect {

    void method1() {
        // ok provides context without redundant message
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error processing file", ex);
        }

        // ok - throws without cause
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("IO error occurred");
        }

        // ok - using getMessage() but not in throw
        try {
            throw new IOException();
        } catch (IOException ex) {
            String message = "Error: " + ex.getMessage();
            System.out.println(message);
        }

        // ok - nested try-catch
        try {
            throw new IOException();
        } catch (IOException ex) {
            try {
                throw new RuntimeException("Error processing", ex);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        // ok - no catch parameter
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error");
        }
    }
}
