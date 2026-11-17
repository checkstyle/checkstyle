/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentMultiLevelInitializers {

    public void sameParameterName(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        new Object() {
            void innerMethod(int port) {
                port = 10; // violation 'Assignment of parameter 'port' is not allowed.'
                new Object() {
                    int port;
                    {
                        port = 8080;
                    }
                };
                port = 20; // violation 'Assignment of parameter 'port' is not allowed.'
            }
        };
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }

    public void differentParameterNames(int port) {
        port = 1; // violation 'Assignment of parameter 'port' is not allowed.'
        new Object() {
            void innerMethod(int value) {
                value = 10; // violation 'Assignment of parameter 'value' is not allowed.'
                new Object() {
                    int value;
                    {
                        value = 8080;
                    }
                };
                value = 20; // violation 'Assignment of parameter 'value' is not allowed.'
            }
        };
        port = 2; // violation 'Assignment of parameter 'port' is not allowed.'
    }
}
