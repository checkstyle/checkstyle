/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.lang.annotation.Annotation;

public class InputUnusedLocalVariableNestedClasses {

    public int V = 1;
    private int G = 1;
    protected int S = 1;
    int Q = 1;

    class AnotherClass {

        void method() {
            int V = 1; // violation
            int G = 1;
            int S = 1; // violation
            int Q = 1; // violation
            InputUnusedLocalVariableNestedClasses obj
                    = new InputUnusedLocalVariableNestedClasses() {
                void method() {
                    Integer.valueOf(V + G + S + Q);
                }
            };
            obj.getClass();
        }
    }

    void method() {
        int variable = 1; // violation
        A.B obj = new A.B() {
            void method() {
                variable += 1;
            }
        };
        obj.getClass();

        int anotherVariable = 2; // violation
        A.B.innerClass obj2 = new A.B().new innerClass() {
            void method() {
                anotherVariable += 1;
            }
        };
        obj2.getClass();

        InputUnusedLocalVariable obj3
                = new InputUnusedLocalVariable() { // cross file checking not supported
        };
        obj3.getClass();
    }

    static class A {
        static class B {
            int variable = 1;

            class innerClass {
                protected int anotherVariable = 1;
                public int k = 1;

                void testInterfaces() {
                    int s = 12; // violation
                    int n = 13; // violation
                    foo obj = new foo() {
                        @Override
                        public Class<? extends Annotation> annotationType() {
                            Integer.valueOf(s);

                            hoo obj = () -> {
                                hoo anotherObj = new hoo() {
                                    @Override
                                    public void anotherMethod() {
                                        Integer.valueOf(n);
                                    }
                                };
                                anotherObj.getClass();
                                String outsideAnonInner = "n is accessible by " + hoo.n;
                                outsideAnonInner.isEmpty();
                            };
                            obj.getClass();
                            return null;
                        }
                    };
                    obj.getClass();
                }
            }
        }
    }
}

@interface foo {
    int s = 2;
}

interface hoo {
    int n = 12;
    void anotherMethod();
}
