/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

public class InputExceptionThrowShouldUseContextEdgeCases3 {

    String instanceField = "instance";
    private final java.io.Closeable closeableField = null;

    void methodTryWithResourceExpression(java.io.Closeable res) throws Exception {
        try (res) {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses res
            throw new RuntimeException(res.toString(), ex);
        }
    }

    void methodTryWithThisResource() throws Exception {
        try (this.closeableField) {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses instanceField
            throw new RuntimeException(instanceField, ex);
        }
    }

    enum MyEnum {
        INSTANCE;
        private String enumField = "val";

        void enumMethod() {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("error", ex);
            }
        }
    }

    interface MyInterface {
        default void ifaceMethod(String ifaceParam) {
            try {
                // empty
            }
            catch (Exception ex) {
                // violation below 'Exception throw should always use context variables.'
                throw new RuntimeException("error", ex);
            }
        }
    }

    void methodMultipleParams(String p1, int p2) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses p1
            throw new RuntimeException(p1, ex);
        }
    }

    {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses instanceField
            throw new RuntimeException(instanceField, ex);
        }
    }

    void methodWrappingNotIdent(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex1) {
            // violation below 'Exception throw should always use context variables.'
            throw new RuntimeException("error", new Exception(ex1));
        }
        catch (Exception ex2) {
            // ok, uses param
            throw new RuntimeException(param, ex2);
        }
        String ex = "after";
    }

    private static void riskyOperation() throws IOException {
    }

    class MyInnerClass {
        private String innerField = "inner";

        void innerMethod() {
            try {
                riskyOperation();
            }
            catch (IOException ex) {
                // ok, uses instanceField from outer class
                throw new RuntimeException(instanceField, ex);
            }
        }
    }
}
