/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases2 {

    void methodNoArgAndRethrow(String param) throws IOException {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException();
        }
        catch (Exception ex2) {
            // ok, rethrow
            throw ex2;
        }
    }

    void methodCatchVarWithContext(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            int dummy = 1;
            String msg = "failed: " + param;
            // ok, msg has context
            throw new RuntimeException(msg, ex);
        }
    }

    void methodCatchVarWithoutAssign(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            String unassigned;
            // ok, uses param
            throw new RuntimeException(param, ex);
        }
    }

    void methodAssignmentInCatch(String param) {
        String dummy = "";
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            dummy = param;
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("no context", ex);
        }
    }

    void methodNestedCatch(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            try {
                riskyOperation();
            }
            catch (Exception inner) {
                String innerMsg = "inner: " + param;
                // ok, uses innerMsg
                throw new RuntimeException(innerMsg, inner);
            }
            // ok, uses param
            throw new RuntimeException(param, ex);
        }
    }

    void methodNestedTypesInCatch(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            class LocalClass {
                void m() {
                    throw new RuntimeException();
                }
            }
            record LocalRecord() {
                void m() {
                    throw new RuntimeException();
                }
            }
            interface LocalInterface {
                default void m() {
                    throw new RuntimeException();
                }
            }
            Runnable r = () -> {
                throw new RuntimeException();
            };
            // ok, uses param
            throw new RuntimeException(param, ex);
        }
    }

    void methodNestedTypesOutsideCatch() {
        class OutsideClass {
            String classField = "field";
        }
        record OutsideRecord() {
            static String recordField = "record";
        }
        interface OutsideInterface {
            String ifaceField = "iface";
        }
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, no context in scope
            throw new RuntimeException("no context", ex);
        }
    }

    void methodMultipleParams(String first, String second) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses second parameter
            throw new RuntimeException("failed: " + second, ex);
        }
    }

    void methodTryWithResource() {
        try (AutoCloseable res = () -> {}) {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses resource
                throw new RuntimeException("failed: " + res, ex);
            }
        }
        catch (Exception ex) {
            // outer catch
        }
    }

    void methodTryWithResourceViolation() {
        try (AutoCloseable res = () -> {}) {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
        catch (Exception ex) {
            // outer catch
        }
    }

    void methodPatternVar(Object obj) {
        if (obj instanceof String patternVar) {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses patternVar
                throw new RuntimeException("failed: " + patternVar, ex);
            }
        }
    }

    void methodPatternVarViolation(Object obj) {
        if (obj instanceof String patternVar) {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    void methodLocalVarOutsideTry() {
        int localVar = 10;
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses localVar
            throw new RuntimeException("failed: " + localVar, ex);
        }
    }

    void methodLocalVarOutsideTryViolation() {
        int localVar = 10;
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("no context", ex);
        }
    }

    static class ClassWithCtor {
        ClassWithCtor(String ctorParam) {
            try {
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
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
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    interface InterfaceWithDefaultMethod {
        default void ifaceMethod(String ifaceParam) {
            try {
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses ifaceParam
                throw new RuntimeException("failed: " + ifaceParam, ex);
            }
        }
    }

    interface InterfaceWithDefaultMethodViolation {
        String IFACE_FIELD = "field";

        default void ifaceMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
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
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
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
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("no context", ex);
            }
        }
    }

    record RecordWithType() {
        static String recordField = "recordVal";

        void recordMethod() {
            try {
                InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
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

    static class OuterWithInstanceField {
        String outerField = "field";

        record InnerRecord() {
            void recordMethod() {
                try {
                    InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
                }
                catch (IOException ex) {
                    // ok, records are static, cannot access outerField
                    throw new RuntimeException("no context", ex);
                }
            }
        }

        class InnerNonStatic {
            static void staticMethod() {
                try {
                    InputExceptionThrowShouldUseContextEdgeCases2.riskyOperation();
                }
                catch (IOException ex) {
                    // ok, static method cannot access outer instance field
                    throw new RuntimeException("no context", ex);
                }
            }
        }
    }

    static class Context {
    }

    void methodTypeMatchesContext(String Context) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            Context ctx = null;
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException(ctx.toString(), ex);
        }
    }

    private static void riskyOperation() throws IOException {
    }
}
