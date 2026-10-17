/*
NoCauseGetMessageInExceptionConstructor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nocausegetmessageinexceptionconstructor;

import java.io.IOException;

// xdoc section -- start
class InputNoCauseGetMessageInExceptionConstructorDefault {

    void method1() throws IOException {
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new IOException("Error: " + ex.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }

    void method2() throws IOException {
        try {
            throw new IOException();
        } catch (IOException exception) {
            throw new IOException(exception.getMessage());
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }

    void method3() throws IOException {
        try {
            throw new IOException();
        } catch (IOException e) {
            throw new IOException("Prefix " + e.getMessage() + " suffix");
                // violation above 'Avoid using '.getMessage()'
                // in throw statement.
        }
    }
}
// xdoc section -- end
