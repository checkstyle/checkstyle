package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameReceiver {
    public void foo4(InputParameterNameReceiver this) {}

    private class Inner {
        public Inner(InputParameterNameReceiver InputParameterNameReceiver.this) {}
    }
}
