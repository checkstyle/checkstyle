/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases7 {

    record RecordWithType() {
        static String recordField = "recordVal";

        void recordMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases7.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    static class OuterWithInstanceField {
        String outerField = "field";

        record InnerRecord() {
            void recordMethod() {
                try {
                    InputExceptionThrowShouldUseContextEdgeCases7.riskyOperation();
                }
                catch (IOException ex) {
                    // ok, records are static, cannot access outerField
                    throw new RuntimeException("no context", ex);
                }
            }
        }
    }

    private static void riskyOperation() throws IOException {
    }
}
