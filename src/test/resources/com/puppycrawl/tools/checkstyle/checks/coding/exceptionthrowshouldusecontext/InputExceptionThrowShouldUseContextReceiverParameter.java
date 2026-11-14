/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

public class InputExceptionThrowShouldUseContextReceiverParameter {

    void run(InputExceptionThrowShouldUseContextReceiverParameter this, String path) {
        try {
            System.out.println(path);
        }
        catch (IllegalStateException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new IllegalArgumentException("failed", ex);
        }
    }

    void okCase(InputExceptionThrowShouldUseContextReceiverParameter this, String path) {
        try {
            System.out.println(path);
        }
        catch (IllegalStateException ex) {
            // ok, uses context variable 'path'
            throw new IllegalArgumentException("failed: " + path, ex);
        }
    }

    void onlyReceiver(InputExceptionThrowShouldUseContextReceiverParameter this) {
        try {
            System.out.println("x");
        }
        catch (IllegalStateException ex) {
            // ok, no context variables available
            throw new IllegalArgumentException("failed", ex);
        }
    }

}
