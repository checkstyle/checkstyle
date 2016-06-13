package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputRequireThisReceiver {
    public void foo4(InputRequireThisReceiver this) {}

    private class Inner {
        public Inner(InputRequireThisReceiver InputRequireThisReceiver.this) {}
    }
}