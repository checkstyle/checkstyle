/*
ParameterAssignment
receiver = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentInitializers {

    void anonymousInstanceInit(int port) {
        Runnable r = new Runnable() {
            int port;
            {
                port = 8080; // ok, field of anonymous class
            }
            @Override
            public void run() {}
        };
    }

    void finalParam(final int port) {
        Runnable r = new Runnable() {
            int port;
            {
                port = 8080; // ok
            }
            @Override
            public void run() {}
        };
    }

    void localClassInstanceInit(int port) {
        class LocalService {
            int port;
            {
                port = 8080; // ok
            }
        }
    }

    void localClassStaticInit(int port) {
        class LocalService {
            static int port;
            static {
                port = 8080; // ok
            }
        }
    }

    void compoundAndUnary(int port) {
        Object o = new Object() {
            int port;
            {
                port += 10; // ok
                port++;     // ok
                --port;     // ok
            }
        };
    }

    // Regression: real parameter assignment must still be flagged
    void realAssignment(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed'
    }

    // Regression: assignment after the inner class, in the outer method
    void afterInnerClass(int port) {
        Object o = new Object() {
            int port;
            { port = 1; } // ok
        };
        port = 2; // violation 'Assignment of parameter 'port' is not allowed'
    }
}
