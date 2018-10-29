package com.puppycrawl.tools.checkstyle.grammar.java8;


public class InputReceiverParameter {
    public void m4(InputReceiverParameter this) {}

    private class Inner {
        public Inner(InputReceiverParameter InputReceiverParameter.this) {}
    }
}
