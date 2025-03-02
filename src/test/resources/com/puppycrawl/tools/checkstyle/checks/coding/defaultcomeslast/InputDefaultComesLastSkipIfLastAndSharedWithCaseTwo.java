/*
DefaultComesLast
skipIfLastAndSharedWithCase = true


*/
package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public class InputDefaultComesLastSkipIfLastAndSharedWithCaseTwo {

    void method(int i) {

        switch (i) {
            case 1:
                break;
            default: // violation 'Default should be last label in the switch.'
                break;
            case 2:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
                break;
            default: // No violation.
                break;
        }
    }

}

@interface InputSkipIfLastAndSharedWithCaseAnnotation
{
    int blag() default 1;
}
