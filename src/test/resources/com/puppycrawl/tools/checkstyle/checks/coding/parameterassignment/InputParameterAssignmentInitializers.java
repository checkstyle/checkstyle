/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

import java.util.function.Consumer;

public class InputParameterAssignmentInitializers {

    static int staticField;
    int instanceField;

    static {
        staticField = 10;
    }
    {
        instanceField = 20;
    }

    public void configure(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        Runnable r = new Runnable() {
            int port;
            {
                port = 8080;
                port += 1;
                port++;
                --port;
            }
            @Override
            public void run() {}
            public void custom(int port) {
                port = 9090; // violation 'Assignment of parameter 'port' is not allowed.'
            }
        };
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }

    public void testLocalClass(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        class LocalService {
            static int port;
            int instancePort;
            {
                instancePort = 8080;
            }
            static {
                port = 8080;
                Consumer<Integer> c = p -> {
                    p = 100; // violation 'Assignment of parameter 'p' is not allowed.'
                };
            }
        }
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }

    public void testLocalRecord(int port) {
        record LocalRecord(int a) {
            static int port;
            static {
                port = 8080;
            }
        }
    }

    public void testNested(int port) {
        new Object() {
            int port;
            {
                port = 1;
                Consumer<Integer> c = p -> {
                    p = 200; // violation 'Assignment of parameter 'p' is not allowed.'
                };
                new Object() {
                    int port;
                    {
                        port = 2;
                    }
                };
            }
        };
    }
}
