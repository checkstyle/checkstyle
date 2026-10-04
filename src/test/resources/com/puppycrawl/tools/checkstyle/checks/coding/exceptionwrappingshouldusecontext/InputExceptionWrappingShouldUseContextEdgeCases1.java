/*
ExceptionWrappingShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionwrappingshouldusecontext;

import java.io.IOException;

public class InputExceptionWrappingShouldUseContextEdgeCases1 {

    static String staticField = "static";
    String instanceField = "instance";

    public InputExceptionWrappingShouldUseContextEdgeCases1(String ctorParam) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception wrapping should always use context variables.'
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
            // violation below 'Exception wrapping should always use context variables.'
            throw new RuntimeException("fail", ex);
        }
    }

    static void staticMethodWithContext() {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses staticField
            throw new RuntimeException(staticField, ex);
        }
    }

    static void staticMethodNoContext() {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception wrapping should always use context variables.'
            throw new RuntimeException("error", ex);
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

    private static void riskyOperation() throws IOException {
    }

    private java.io.InputStream openStream() throws Exception {
        return null;
    }
}
