package com.puppycrawl.tools.checkstyle;

public class SyntacticallyInvalidInputForSuppressWarningsHolder {
    public static void main(String[] args) {
        private long foo;
        protected int bar = 1;
        private int
    }

    public static class Inner {
        private Long baz;
    }
}
