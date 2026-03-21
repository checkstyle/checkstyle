/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisReceiver {
    public void foo4(InputRequireThisReceiver this) {}

    private class Inner {
        public Inner(InputRequireThisReceiver InputRequireThisReceiver.this) {}
    }
}
