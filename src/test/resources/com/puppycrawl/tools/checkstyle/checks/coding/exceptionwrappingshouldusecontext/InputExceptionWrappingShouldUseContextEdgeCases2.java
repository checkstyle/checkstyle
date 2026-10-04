/*
ExceptionWrappingShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionwrappingshouldusecontext;

import java.io.IOException;

public class InputExceptionWrappingShouldUseContextEdgeCases2 {

    void methodNoArgException(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, no wrapping
            throw new RuntimeException();
        }
    }

    void methodThrowExistingException(String param) throws IOException {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, rethrow
            throw ex;
        }
    }

    void methodCatchVarWithContext(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
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

    void methodNestedCatch(String param) {
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            try {
                riskyOperation();
            }
            catch (Exception inner) {
                // inner catch
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

    void methodNestedTypesOutsideCatch(String param) {
        class OutsideClass {}
        record OutsideRecord() {}
        interface OutsideInterface {}
        try {
            riskyOperation();
        }
        catch (IOException ex) {
            // ok, uses param
            throw new RuntimeException(param, ex);
        }
    }

    private static void riskyOperation() throws IOException {
    }
}
