/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableLambdaAnonInner {

    Object fieldAnon = new Object() {
        int fieldInAnon = 1;
    };

    void testLocalAnonInnerClass() {
        int usedVar = 1; // violation 'Unused local variable 'usedVar''
        Object o = new Object() { // violation 'Unused local variable 'o''
            void inner() {
                int unusedInAnon = 2; // violation 'Unused local variable 'unusedInAnon''
            }
        };
    }

    Runnable fieldLambda = () -> {
        Object o = new Object() { // violation 'Unused local variable 'o''
            void method() {
                int unusedInField = 1; // violation 'Unused local variable 'unusedInField''
            }
        };
    };

    void testLambdaContainingAnon() {
        int outerUsed = 1; // violation 'Unused local variable 'outerUsed''
        Runnable r = () -> { // violation 'Unused local variable 'r''
            int lambdaVar = 2; // violation 'Unused local variable 'lambdaVar''
            Object o = new Object() { // violation 'Unused local variable 'o''
                void inner() {
                    int anonVar = 3; // violation 'Unused local variable 'anonVar''
                }
            };
        };
    }
}
