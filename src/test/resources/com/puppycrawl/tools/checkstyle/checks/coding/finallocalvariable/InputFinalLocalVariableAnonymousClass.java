package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableAnonymousClass {
    public void test() {
        Object testSupport = new Object() {
            @Override
            public String toString() {
                final String dc = new String();
                return dc;
            }
        };
        testSupport.toString();
    }
}
