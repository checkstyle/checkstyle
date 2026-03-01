/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

class InputNoGetMessageInThrowSiblingTraversal {
    void method() throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            int x = 0;
            String s = "test";
            throw new IOException(ex.getMessage());
            // violation above 'Avoid using '.getMessage()'
            // in throw statement.
        }
    }
    void method2() throws IOException {
        try {
            throw new IOException();
        } catch (java.io.IOException ex) {
            throw new java.io.IOException("Error: " + ex.getMessage());
            // violation above 'Avoid using '.getMessage()'
        }
    }

    void method3() throws IOException {
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new java.io.IOException("Error: " + ex.getMessage());
            // violation above 'Avoid using '.getMessage()'
        }
    }

    void method4() throws IOException {
        try {
            throw new IOException();
        } catch (java.io.IOException ex) {
            throw new IOException("Error: " + ex.getMessage());
            // violation above 'Avoid using '.getMessage()'
        }
    }

    void methodNoViolation() throws Exception {
        try {
            throw new IOException();
        } catch (java.io.IOException ex) {
            throw new java.lang.RuntimeException("Error: " + ex.getMessage());
        }
    }

    public void methodNoViolation2() throws IOException {
        try {
            throw new IOException();
        } catch (java.io.IOException ex) {
            throw new java.io.IOException("Error processing", ex);
        }
    }
}
