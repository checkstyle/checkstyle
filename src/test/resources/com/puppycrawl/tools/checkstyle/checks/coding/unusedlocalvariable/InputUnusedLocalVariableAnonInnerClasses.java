/*
UnusedLocalVariable


*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.lang.annotation.Annotation;

public class InputUnusedLocalVariableAnonInnerClasses {

    static int a = 12, b = 13, c = 14;

    public void testAnonymousInnerClass() {
        int a = 12; // violation
        int b = 12; // violation
        int k = 14; // ok
        Test obj = new Test() { // violation
            int a = 2;

            private void testSameName(int s) {
                s = a + InputUnusedLocalVariable.a;
                Test obj = new Test() { // violation
                    int b = 1;

                    private void testSameNameNested(int s) {
                        s = b + InputUnusedLocalVariable.b + InputUnusedLocalVariable.a + a + k;
                    }
                };
            }
        };

        Test obj2; // violation
        obj2 = new Test() {
            int a = 1;
            int b = 1;
            int c = 0; // ok

            private void testSameName(int s) {
                s = a + b + this.a + this.b + InputUnusedLocalVariableAnonInnerClasses.a
                        + InputUnusedLocalVariableAnonInnerClasses.b;
            }
        };
    }

    {
        int m = 12; // violation
        int l = 2; // violation
        d obj = new d() {
            void method() {
                m += l;
            }
        };
        obj.getClass();
    }

    static {
        int a = 1;
        int x = 2;
        int h = 3; // violation
        m.Test obj = new m().new Test() {
            void method() {
                boolean v = // violation
                        a == x == (h == 2);
            }
        };
        obj.getClass();
    }

    class Test {
        private int k = 1;
    }

    static class m {
        public int x = 1;

        class Test {
            public int h = 12;
        }
    }

    class d {
        protected int m = 11;
        int l = 14;
    }

}

class testNestedClasses {
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
            testNestedClasses obj = new testNestedClasses() {
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

class anotherClass {
    class testDepth {
    }
}

class testDepth {

    int variable = 1;

    class k {
        class n {
        }
    }

    class n {
        int r = 1;
    }

    class f {
        class n {
        }
    }

    void method() {
        int r = 1; // violation
        n obj = new n() {
            void method() {
                r += 12;
            }
        };
        obj.getClass();
    }

    class r {
        class k {
            int p = 12;
        }
    }

    static class p {
        class r {
            class k {
                int a = 13;
                void method() {
                    int a = 1; // violation
                    int p = 1;
                    r.k obj = new r().new k() {
                        void method() {
                            a += p;
                        }
                    };
                    obj.getClass();
                }
            }
        }
    }
    class P {
        void method() {
            int a = 1; // violation
            Q obj = new Q() {
                void method() {
                    a += 1;
                }

            };
            obj.getClass();
        }
    }

    class Q {
        int a = 1;
    }

    class PR {
        class P {
            class Q {
            }
        }
    }
}

class Clazz {
    void method() {
        int variable = 1; // violation
        testDepth obj = new testDepth() {
            void method() {
                variable += 1;
            }
        };
        obj.getClass();
    }
}
