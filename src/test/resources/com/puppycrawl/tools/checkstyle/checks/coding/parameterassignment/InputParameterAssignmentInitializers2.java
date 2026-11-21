/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentInitializers2 {

    public InputParameterAssignmentInitializers2(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        new Object() {
            int port;
            {
                port = 8080;
            }
        };
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }
}
