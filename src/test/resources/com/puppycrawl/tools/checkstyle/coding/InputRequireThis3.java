package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputRequireThis3 {
    interface AnonWithEmpty {
        public void fooEmpty();
    }

    void method() {
        AnonWithEmpty foo = new AnonWithEmpty() {

            public void emptyMethod() {
            }

            @Override
            public void fooEmpty() {
                int a = doSideEffect();
            }

            public int doSideEffect() {
                return 1;
            }
        };
    }
}
