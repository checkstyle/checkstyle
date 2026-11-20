/*
ParameterAssignment
receiver = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentInitializersRecord {
    void localRecord(int port) {
        record LocalRecord(int a) {
            static int port;
            static {
                port = 8080;
            }
        }
    }
}
