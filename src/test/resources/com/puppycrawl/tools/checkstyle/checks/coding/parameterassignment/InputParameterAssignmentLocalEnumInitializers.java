/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentLocalEnumInitializers {

    public void instanceInitializer(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        enum LocalEnum {
            ONE;
            int port;
            {
                port = 8080;
            }
        }
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }

    public void staticInitializer(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        enum LocalEnum {
            ONE;
            static int port;
            static {
                port = 8080;
            }
        }
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }
}
