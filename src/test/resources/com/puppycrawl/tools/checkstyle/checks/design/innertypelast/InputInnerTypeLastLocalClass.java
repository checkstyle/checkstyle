/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastLocalClass {

    void method() {
        class Local {
            class Inner {
            }

            int field; // violation 'Init blocks, constructors, fields and methods'

            void run() { // violation 'Init blocks, constructors, fields and methods'
            }
        }

        int afterLocal = 1;
        record LocalRecord(int value) {
        }
        String afterRecord = String.valueOf(afterLocal);
    }

    void anonymousInMethod() {
        Object o = new Object() {
            class Inner {
            }

            void run() { // violation 'Init blocks, constructors, fields and methods'
            }
        };
    }

    Runnable lambda = () -> {
        class InLambda {
            interface Inner {
            }

            int field; // violation 'Init blocks, constructors, fields and methods'
        }
    };
}
