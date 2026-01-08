/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableLambdaAnonInner {

    Runnable r = () -> {
        new Object() {
            void m() {
                int x = 10; // violation 'Unused local variable 'x''
            }
        };
    };

    Object fieldObj = new Object() {
        void m() {
            int x = 10; // violation 'Unused local variable 'x''
        }
    };

    void test() {
        Object localObj = new Object() {
            void m() {
                int y = 5; // violation 'Unused local variable 'y''
            }
        };
    }
}
