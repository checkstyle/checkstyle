package com.puppycrawl.tools.checkstyle.checks.naming.abbrevationaswordinname;

public class InputAbbreviationAsWordInNameReceiver {
    public void foo4(InputAbbreviationAsWordInNameReceiver this) {}

    private class Inner {
        public Inner(InputAbbreviationAsWordInNameReceiver InputAbbreviationAsWordInNameReceiver.this) {}
    }
}
