/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousEmpty {

    private int bar; private int Override;
    int a;

    interface AnonWithEmpty {
        public void fooEmpty();
    }

    void method() {
        AnonWithEmpty foo = new AnonWithEmpty() {

            public void emptyMethod() {
            }

            @Override
            public void fooEmpty() {
                int a = doSideEffect(); // violation
            }

            public int doSideEffect() {
                return bar; // violation
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
                foobar++; // violation
            }
        };
    }

    void method2() {
        int a = 1;
        InputRequireThisAnonymousEmpty obj =
            new InputRequireThisAnonymousEmpty() {
                void method() {
                    a += 1;
                }
            };
    }

    void anotherMethod() {
        int var1 = 12;
        int var2 = 13;
        Foo obj = new Foo() {
            void method() {
                var2 += var1;
            }
        };
        obj.getClass();
    }
}

class SharkFoo {
    int var1 = 12;
}

class Foo {
    int var2 = 13;

    class SharkFoo {
    }
}
