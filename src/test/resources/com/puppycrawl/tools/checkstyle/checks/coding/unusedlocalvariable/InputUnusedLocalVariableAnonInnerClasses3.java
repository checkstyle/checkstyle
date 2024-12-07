/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAnonInnerClasses3 {

    static class m {
        public int a = 12;
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

                m obj = new m() { // ok, inst var
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
