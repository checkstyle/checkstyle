package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousEmpty {

    private int bar;

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
                return bar;
            }
        };

        new AnonWithEmpty() {
            int anonMember = 0;

            @Override
            public void fooEmpty() {
                new AnonWithEmpty() {

                    @Override
                    public void fooEmpty() {
                        anonMember++;
                    }
                };
            }
        };

        new AnonWithEmpty() {
            int foobar = 1;
            @Override
            public void fooEmpty() {
                foobar++;
            }
        };
    }
}
