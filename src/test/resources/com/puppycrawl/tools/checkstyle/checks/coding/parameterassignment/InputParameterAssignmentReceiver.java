package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentReceiver {
    public void foo4(InputParameterAssignmentReceiver this) {}

    private class Inner {
        public Inner(InputParameterAssignmentReceiver InputParameterAssignmentReceiver.this) {}
    }
}
