/*
UnusedLocalVariable
allowUnnamedVariables = false

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
        int r = 1; // violation, 'Unused local variable'
        n obj = new n() {
            void method() {
                r += 12;
            }
        };
        obj.getClass();
    }

    class r {
        class k {
            int s = 12;
        }
    }

    static class s {
        class r {
            class k {
                int a = 13;

                void method() {
                    int a = 1; // violation, 'Unused local variable'
                    int s = 1;
                    r.k obj = new r().new k() {
                        void method() {
                            a += s;
                        }
                    };
                    obj.getClass();
                }
            }
        }
    }

    class P {
        void method() {
            int a = 1; // violation, 'Unused local variable'
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
        int variable = 1; // violation, 'Unused local variable'
        InputUnusedLocalVariableDepthOfClasses obj = new InputUnusedLocalVariableDepthOfClasses() {
            void method() {
                variable += 1;
            }
        };
        obj.getClass();
    }
}
