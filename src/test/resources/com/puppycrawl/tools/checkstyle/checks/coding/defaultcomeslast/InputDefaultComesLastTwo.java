/*
DefaultComesLast
skipIfLastAndSharedWithCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public class InputDefaultComesLastTwo {

    void method(int i) {

         switch (i) {
            default: // violation 'Default should be last label in the switch.'
                break;
            case 1:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
                break;
            default: // violation 'Default should be last label in the switch.'
            case 5:
            case 6:
                break;
            case 7:
                break;
        }

    }
}

@interface InputDefaultComesLastAnnotation
{
    int blag() default 1;
}
