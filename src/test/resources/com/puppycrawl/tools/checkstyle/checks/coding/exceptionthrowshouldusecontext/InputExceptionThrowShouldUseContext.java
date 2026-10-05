/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContext {
    private String field = "context";

    void method1(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("unable to process ", ex1);
        }
        catch (Exception ex2) {
            throw new RuntimeException("unable to process " + someParameter, ex2);
        }
    }

    void method2(String someParameter) throws IOException {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            String msg = "unable to process " + someParameter;
            throw new RuntimeException(msg, ex1);
        }
        catch (Exception ex2) {
            throw ex2;
        }
    }

    void method3(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("error: " + ex1.getMessage());
        }
        catch (Exception ex2) {
            String context = "some value";
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("unable to process" + context, ex2);
        }
    }

    void method4() {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            throw new RuntimeException("error " + field, ex1);
        }
        catch (Exception ex2) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("error", ex2);
        }
    }

    void methodCatchVarNoContext(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            String msg = "unable to process";
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException(msg, ex);
        }
    }

    void methodInLambda(String outerParam) {
        java.util.List<String> list = java.util.Collections.emptyList();
        list.forEach(item -> {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                throw new RuntimeException("error " + outerParam, ex);
            }
        });
    }

    private void riskyOperation() throws IOException {
    }

    static class NoContextClass {
        static void parameterlessMethod() {
            try {
                // do nothing
            }
            catch (Exception ex) {
                throw new RuntimeException("error", ex);
            }
        }
    }
}
