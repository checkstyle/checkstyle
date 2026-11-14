/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextReceiverParameter {

    void withContext(InputExceptionThrowShouldUseContextReceiverParameter this, String path) {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // violation below 'Exception throw should always use context variables.'
            throw new IllegalStateException("failed", ex1);
        }
        catch (IllegalArgumentException ex2) {
            // ok - uses 'path'
            throw new IllegalStateException(path, ex2);
        }
    }

    void onlyReceiver(InputExceptionThrowShouldUseContextReceiverParameter this) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok - receiver parameter is not a context variable
            throw new IllegalStateException("failed", ex);
        }
    }

    class Inner {

        Inner(InputExceptionThrowShouldUseContextReceiverParameter
                InputExceptionThrowShouldUseContextReceiverParameter.this, int count) {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new IllegalStateException("failed", ex);
            }
        }
    }

    static void riskyOperation() throws IOException {
        throw new IOException("risky");
    }
}
