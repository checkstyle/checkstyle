/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases5 {

    void methodNestedTypesOutsideCatch() {
        class OutsideClass {
            String classField = "field";
        }
        record OutsideRecord() {
            static String recordField = "record";
        }
        interface OutsideInterface {
            String interfaceField = "interface";
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

    private static void riskyOperation() throws IOException {
    }
}
