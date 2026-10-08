/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public record InputExceptionThrowShouldUseContextRecord(String name, int value) {

    public InputExceptionThrowShouldUseContextRecord {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("error", ex1);
        }
        catch (Exception ex2) {
            // ok - uses 'name'
            throw new RuntimeException(name, ex2);
        }
        catch (Throwable ex3) {
            // ok - uses 'value'
            throw new RuntimeException(String.valueOf(value), ex3);
        }
    }

    private static void riskyOperation() throws IOException {
    }

    record EmptyRecord() {
        public EmptyRecord {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok - no components available
                throw new RuntimeException("error", ex);
            }
        }
    }
}
