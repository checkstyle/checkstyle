package com.puppycrawl.tools.checkstyle.checks.naming;

public class InputAbbreviationAsWordReceiver {
    public void foo4(InputAbbreviationAsWordReceiver this) {}

    private class Inner {
        public Inner(InputAbbreviationAsWordReceiver InputAbbreviationAsWordReceiver.this) {}
    }
}