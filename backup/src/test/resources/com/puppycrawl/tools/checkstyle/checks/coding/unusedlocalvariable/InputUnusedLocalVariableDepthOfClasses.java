/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableDepthOfClasses {

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

class anotherClass {
    class InputUnusedLocalVariableDepthOfClasses {
    }
}

class Clazz {
    void method() {
        int variable = 1; // violation
        InputUnusedLocalVariableDepthOfClasses obj = new InputUnusedLocalVariableDepthOfClasses() {
            void method() {
                variable += 1;
            }
        };
        obj.getClass();
    }
}
