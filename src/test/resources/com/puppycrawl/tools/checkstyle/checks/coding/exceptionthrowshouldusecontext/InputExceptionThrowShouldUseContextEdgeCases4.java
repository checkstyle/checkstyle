/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases4 {

    void methodNestedIfInCatch(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            if (param != null) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    RuntimeException methodReturnNewException() {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, return statement is not throw
            return new RuntimeException();
        }
        return null;
    }

    static class CustomException extends RuntimeException {
        CustomException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    void methodExceptionNameMatchesVariable(String CustomException) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new CustomException("no context", ex);
        }
    }

    static class ClassWithFieldNamedEx {
        String ex = "fieldEx";

        void test() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases4.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    static class ClassWithOnlyInstanceFields {
        String instanceField = "field";

        static void staticMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases4.riskyOperation();
            }
            catch (IOException ex) {
                // ok, static method has no context
                throw new RuntimeException("no context", ex);
            }
        }
    }

    static class OuterClassWithFields {
        String outerField = "outer";

        static class InnerClass {
            static void staticInnerMethod() {
                try {
                    InputExceptionThrowShouldUseContextEdgeCases4.riskyOperation();
                }
                catch (IOException ex) {
                    // ok, static method cannot access outer instance field
                    throw new RuntimeException("no context", ex);
                }
            }
        }
    }

    private static void riskyOperation() throws IOException {
    }
}
