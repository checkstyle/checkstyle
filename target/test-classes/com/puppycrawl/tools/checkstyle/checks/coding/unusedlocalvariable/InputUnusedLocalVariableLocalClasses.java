/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableLocalClasses {

    int a = 12;

    void foo() {
        int a = 12; // violation, 'Unused local variable'
        int ab = 1; // violation, 'Unused local variable'

        class asd {
            InputUnusedLocalVariableLocalClasses a = new InputUnusedLocalVariableLocalClasses() {
                void asd() {
                    System.out.println(a);
                }
            };
        }
    }

}
