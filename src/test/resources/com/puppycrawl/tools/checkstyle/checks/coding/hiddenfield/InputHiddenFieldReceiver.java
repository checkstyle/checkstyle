package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldReceiver {
    public void foo4(InputHiddenFieldReceiver this) {}

    private class Inner {
        public Inner(InputHiddenFieldReceiver InputHiddenFieldReceiver.this) {}
    }
}
