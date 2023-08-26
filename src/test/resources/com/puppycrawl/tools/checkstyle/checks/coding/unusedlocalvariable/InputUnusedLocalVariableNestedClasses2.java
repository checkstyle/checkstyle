/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableNestedClasses2 {

    static class A {
        static class B {
            void foo() {
                class C {
                    int a = 12;
                }
            }

            class C {
                int q = 13;
            }
        }
    }
}

class d {
    void testLocalClasses() {
        int a = 12;
        int q = 13; // violation
        InputUnusedLocalVariableNestedClasses2.A.B.C obj = // violation
                new InputUnusedLocalVariableNestedClasses2.A.B().new C() {
            void method() {
                q += a;
            }
        };
    }
}

class InputUnusedLocalVariableSameNameLen1 {
    class A {
        class B {
            class C {
                int b = 12;

                void method() {
                    int b = 12; // violation
                    int a = 12;
                    C obj = new C() {
                        void method() {
                            b += a;
                        }
                    };
                    obj.getClass();
                }

                void anotherMethod() {
                    int b = 12; // violation
                    InputUnusedLocalVariableSameNameLen1.A.B.C obj =
                            new com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable
                                    .InputUnusedLocalVariableSameNameLen1()
                                    .new A().new B().new C() {
                        void anotherMethod() {
                            b += 1;
                        }
                    };
                    obj.anotherMethod();
                }
            }
        }

        void method() {
            int a = 12; // ok, cross file detection not supported
            InputUnusedLocalVariable obj
                    = new com.puppycrawl.tools.checkstyle.checks.coding
                    .unusedlocalvariable.InputUnusedLocalVariable() {
                void method() {
                    a += 12;
                }
            };
            obj.getClass();
        }
    }

    interface Tester {
        void test();
    }

    interface TU<U> {
        public void foo(U u);
    }

    public static <U> void exec(TU<U> lambda, U x) {
        lambda.foo(x);
    }

    void test1() {
        final Integer N1 = 1;
        class A {
            Integer n2 = 20;

            void test() {
                final Integer N2 = 2;
                class B {
                    void test() {
                        final Integer N3 = 3;
                        exec((final Integer x) -> new Tester() {
                            public void test() {
                                int s = Integer.valueOf(x + n2 + N1 + N2 + N3); // violation
                            }
                        }.test(), 30);
                    }
                }
                new B().test();
            }
        }
        new A().test();
    }
}
