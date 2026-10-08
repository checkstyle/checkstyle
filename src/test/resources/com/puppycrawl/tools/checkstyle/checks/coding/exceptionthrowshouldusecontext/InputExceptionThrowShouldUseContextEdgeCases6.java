/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases6 {

    static class ClassWithCtor {
        ClassWithCtor(String ctorParam) {
            try {
                InputExceptionThrowShouldUseContextEdgeCases6.riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses ctorParam
                throw new RuntimeException("failed: " + ctorParam, ex);
            }
        }
    }

    static class ClassWithCtorViolation {
        ClassWithCtorViolation(String ctorParam) {
            try {
                InputExceptionThrowShouldUseContextEdgeCases6.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    interface InterfaceWithDefaultMethod {
        default void interfaceMethod(String interfaceParam) {
            try {
                InputExceptionThrowShouldUseContextEdgeCases6.riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses interfaceParam
                throw new RuntimeException("failed: " + interfaceParam, ex);
            }
        }
    }

    interface InterfaceWithDefaultMethodViolation {
        String INTERFACE_FIELD = "field";

        default void interfaceMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases6.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    enum EnumWithType {
        INSTANCE;
        String enumField = "enumVal";

        void enumMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases6.riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses enumField
                throw new RuntimeException("failed: " + enumField, ex);
            }
        }
    }

    enum EnumWithTypeViolation {
        INSTANCE;
        String enumField = "enumVal";

        void enumMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases6.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    static {
        java.util.function.Consumer<String> consumer = lambdaParam -> {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses lambdaParam
                throw new RuntimeException("failed: " + lambdaParam, ex);
            }
        };
        java.util.function.Consumer<String> consumerViolation = lambdaParam -> {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        };
    }

    private static void riskyOperation() throws IOException {
    }
}
