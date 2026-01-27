/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

// xdoc section -- start
class InputNoGetMessageInCatchThrowNoViolations {

    void method1() throws IOException {
        // Catch with no throw statement at all
        try {
            throw new IOException();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void method2() throws IOException {
        // Catch with throw but no getMessage
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error", ex);
        }
    }

    void method3() throws IOException {
        // Empty catch block
        try {
            throw new IOException();
        } catch (IOException ex) {
            // empty
        }
    }

    void method4() throws IOException {
        // Catch with only variable assignment
        try {
            throw new IOException();
        } catch (IOException ex) {
            String msg = "error";
            System.out.println(msg);
        }
    }

    void method5() throws IOException {
        // Catch with method call but not getMessage
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException(ex.toString(), ex);
        }
    }

    void method6() throws IOException {
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
