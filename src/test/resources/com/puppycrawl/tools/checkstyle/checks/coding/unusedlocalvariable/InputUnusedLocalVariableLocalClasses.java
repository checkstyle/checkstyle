/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableLocalClasses {

    int a = 12;

    void foo() {
        int a = 12; // violation
        int ab = 1; // violation

        class asd {
            InputUnusedLocalVariableLocalClasses a = new InputUnusedLocalVariableLocalClasses() {
                void asd() {
                    System.out.println(a);
                }
            };
        }
    }

}
