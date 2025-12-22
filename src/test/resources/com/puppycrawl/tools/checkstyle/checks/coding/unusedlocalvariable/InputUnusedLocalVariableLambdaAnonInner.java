/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableLambdaAnonInner {

    void test() {
        Runnable r = () -> { // violation 'Unused local variable 'r''
            int unusedInLambda = 1; // violation 'Unused local variable 'unusedInLambda''

            Object o = new Object() { // violation 'Unused local variable 'o''
                void inner() {
                    int unusedInAnon = 2; // violation 'Unused local variable 'unusedInAnon''
                }
            };
        };
    }
}
