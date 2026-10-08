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

        class InnerClassWithStaticMethod {
            static void staticMethod() {
                try {
                    InputExceptionThrowShouldUseContextEdgeCases7.riskyOperation();
                }
                catch (IOException ex) {
                    // ok, static method cannot access outerField
                    throw new RuntimeException("no context", ex);
                }
            }
        }
    }

    static class ContextType {
    }

    void methodWithTypeNameAsContext(ContextType ContextType) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            ContextType local = null;
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("error" + local, ex);
        }
    }

    private static void riskyOperation() throws IOException {
    }
}
