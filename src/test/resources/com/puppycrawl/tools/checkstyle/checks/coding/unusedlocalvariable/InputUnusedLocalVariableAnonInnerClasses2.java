/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAnonInnerClasses2 {

    static class m {
        static class h {
            public int a = 12;
        }
    }

    static class j {
        static void method() {
            int a = 1000;
            int s = 13; // violation
            int q = 14;
            m.h obj = new m.h() {
                @Override
                void method() {
                    Integer.valueOf(a + s);
                }

                m.h obj = new m.h() { // ok, inst var
                    @Override
                    void method() {
                        Integer.valueOf(q);
                    }
                };

            };
            obj.method();
        }

        static class m {
            static class h {
                int s = 12;
                void method() {
                }
            }
        }
    }

    static class jasper {
        static class m {
            static class h {
                int q = 12;
            }
        }
    }
}
