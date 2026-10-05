/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases1 {

    static String staticField = "static";
    String instanceField = "instance";

    public InputExceptionThrowShouldUseContextEdgeCases1(String ctorParam) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("no context used", ex);
        }
    }

    static class NoContextClass {
        static void parameterlessMethod() {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, no context variables exist in scope
                throw new RuntimeException("error", ex);
            }
        }

        private static void riskyOperation() throws IOException {
        }
    }

    void methodTypeCast(String param) {
        try {
            riskyOperation();
        }
        catch (Exception ex) {
            // ok, uses param with typecast
            throw new RuntimeException(param, (RuntimeException) (Exception) ex);
        }
    }

    void methodTryWithResources() throws Exception {
        try (java.io.InputStream stream = openStream()) {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("fail", ex);
        }
    }

    static void staticMethod() {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // ok, uses staticField
            throw new RuntimeException(staticField, ex1);
        }
        catch (Exception ex2) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("error", ex2);
        }
    }

    void methodPatternVariable(Object obj) {
        if (obj instanceof String str) {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses str
                throw new RuntimeException(str, ex);
            }
        }
    }

    void methodLambdaSingleParam() {
        java.util.function.Function<String, Runnable> fn = item -> () -> {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses item
                throw new RuntimeException(item, ex);
            }
        };
    }

    void methodZeroArgThrow(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException();
        }
    }

    void methodShadowedCatchParam() {
        {
            String ex = "shadowed";
        }
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

    private java.io.InputStream openStream() throws Exception {
        return null;
    }
}
