/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAnonInnerClasses3 {

    static class m {
        void method() {
            int a = 12; // violation, 'Unused local variable'
        }
    }

    static class myClass {
        static void method() {
            int a = 12;
            m obj = new m() { // violation, 'Unused local variable'
                @Override
                void method() {
                    Integer.valueOf(a);
                }
            };
        }
    }

    static class j {
        static void method() {
            int a = 1000;
            int s = 13; // violation, 'Unused local variable'
            int q = 14;
            m obj = new m() {
                @Override
                void method() {
                    Integer.valueOf(a + s);
                }

                m obj = new m() { //anonymous instance field
                    @Override
                    void method() {
                        Integer.valueOf(q);
                    }
                };

            };
            obj.method();
        }

        static class m {
            int s = 12;

            void method() {
            }
        }
    }

    static class jasper {
        static class m {
            int q = 12;
        }
    }
}
