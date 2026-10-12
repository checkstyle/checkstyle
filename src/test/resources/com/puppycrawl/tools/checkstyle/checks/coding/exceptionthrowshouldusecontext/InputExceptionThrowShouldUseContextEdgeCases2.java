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

    private static void riskyOperation() throws IOException {
    }
}
