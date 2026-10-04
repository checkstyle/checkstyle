/*
ExceptionWrappingShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionwrappingshouldusecontext;

import java.io.IOException;

public class InputExceptionWrappingShouldUseContext {
    private String field = "context";

    void method1(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception wrapping should always use context variables.'
            throw new RuntimeException("unable to process ", ex);
        }
    }

    void method2(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            throw new RuntimeException("unable to process " + someParameter, ex);
        }
    }

    void method3(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            String msg = "unable to process " + someParameter;
            throw new RuntimeException(msg, ex);
        }
    }

    void method4(String someParameter) throws IOException {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            throw ex;
        }
    }

    void method5(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            throw new RuntimeException("error: " + ex.getMessage());
        }
    }

    void method6() {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            throw new RuntimeException("error " + field, ex);
        }
    }

    void method7() {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // violation below 'Exception wrapping should always use context variables.'
            throw new RuntimeException("error", ex);
        }
    }

    void methodCatchVarNoContext(String someParameter) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            String msg = "unable to process";
            // violation below 'Exception wrapping should always use context variables.'
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
