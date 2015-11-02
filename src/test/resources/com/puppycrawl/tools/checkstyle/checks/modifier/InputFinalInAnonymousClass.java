package com.puppycrawl.tools.checkstyle.checks.modifier;

public class InputFinalInAnonymousClass {
    public static abstract class Example {
        public abstract void innerTest();

        public final void test() {
        }
    }

    public static void test() {
        new Example() {
            @Override
            public final void innerTest() { // violation
            }
        };
    }
}
