/*
ExceptionWrappingShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionwrappingshouldusecontext;

import java.io.IOException;

public record InputExceptionWrappingShouldUseContextRecord(String name, int value) {

    public InputExceptionWrappingShouldUseContextRecord {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok - uses 'name'
            throw new RuntimeException(name, ex);
        }
    }

    private static void riskyOperation() throws IOException {
    }

    record RecordViolation(String component) {
        public RecordViolation {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception wrapping should always use context variables.'
                throw new RuntimeException("error", ex);
            }
        }
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
