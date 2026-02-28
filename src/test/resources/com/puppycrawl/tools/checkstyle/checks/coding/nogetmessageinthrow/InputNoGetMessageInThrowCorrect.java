/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

class InputNoGetMessageInThrowCorrect {

    void method1() throws IOException {
        // ok provides context without redundant message
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error processing file", ex);
        }
    }

    void method2() throws IOException {
        // ok - throws without cause
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("IO error occurred");
        }
    }

    void method3() throws IOException {
        // ok - using getMessage() but not in throw
        try {
            throw new IOException();
        } catch (IOException ex) {
            String message = "Error: " + ex.getMessage();
            System.out.println(message);
        }
    }

    void method4() throws IOException {
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
    }

    void method5() throws IOException {
        // ok - no catch parameter
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error");
        }
    }
}
