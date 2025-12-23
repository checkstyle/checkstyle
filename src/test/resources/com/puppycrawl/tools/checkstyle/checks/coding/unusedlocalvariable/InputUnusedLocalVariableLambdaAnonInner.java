/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableLambdaAnonInner {

    // Case 1: Anonymous class NOT inside SLIST (field level) - should NOT be treated as local
    // This tests the SLIST check - if mutated to always true, this would be wrongly processed
    Object fieldAnon = new Object() {
        int fieldInAnon = 1; // This is NOT a local variable, should not be flagged
    };

    // Case 2: Anonymous class inside SLIST (method body) - IS local
    void testLocalAnonInnerClass() {
        int usedVar = 1; // violation 'Unused local variable 'usedVar''
        Object o = new Object() { // violation 'Unused local variable 'o''
            void inner() {
                int unusedInAnon = 2; // violation 'Unused local variable 'unusedInAnon''
            }
        };
    }

    // Case 3: Lambda at field level containing anon class - tests LAMBDA tracking
    Runnable fieldLambda = () -> {
        Object o = new Object() { // violation 'Unused local variable 'o''
            void method() {
                int unusedInFieldLambdaAnon = 1; // violation 'Unused local variable 'unusedInFieldLambdaAnon''
            }
        };
    };

    // Case 4: Nested lambda with anonymous class inside method
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
