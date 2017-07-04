package com.puppycrawl.tools.checkstyle.grammars.java8;


public class InputReceiverParameter {
    public void m4(InputReceiverParameter this) {}

    private class Inner {
        public Inner(InputReceiverParameter InputReceiverParameter.this) {}
    }
}
