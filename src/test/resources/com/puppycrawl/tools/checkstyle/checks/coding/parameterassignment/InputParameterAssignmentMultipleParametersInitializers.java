/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentMultipleParametersInitializers {

    public void partialShadowing(int port, String name) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        name = "a"; // violation 'Assignment of parameter 'name' is not allowed.'
        new Object() {
            int port;
            {
                port = 8080;
            }
        };
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
        name = "b"; // violation 'Assignment of parameter 'name' is not allowed.'
    }
}
