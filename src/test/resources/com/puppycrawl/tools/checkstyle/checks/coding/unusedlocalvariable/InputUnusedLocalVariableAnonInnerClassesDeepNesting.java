/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAnonInnerClassesDeepNesting {
    public void method() {
        Object outer = new Object() {
            Object inner1 = new Object() { // level 2
                void method1() {
                    int a = 10; // violation, 'Unused local variable'
                    Runnable r1 = new Runnable() {
                        public void run() {
                            int b = 20; // violation, 'Unused local variable'
                        }
                    };
                    r1.run();
                }
            };
        };
        outer.toString();
    }
}
