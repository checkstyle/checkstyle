package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersReceiver {
    public void foo4(InputFinalParametersReceiver this) {}

    private class Inner {
        public Inner(InputFinalParametersReceiver InputFinalParametersReceiver.this) {}
    }
}
