/*
DefaultComesLast
skipIfLastAndSharedWithCase = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public class InputDefaultComesLastSkipIfLastAndSharedWithCaseOne
{
    void method(int i) {
        switch (i) {
            case 1:
            default: // No violation with the new option is expected
                break;
            case 2:
                break;
        }

        switch (i) {
            case 1:
            default: // violation 'Default should be last label in the case group.'
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            default: // violation 'Default should be last label in the case group.'
            case 1:
                break;
            case 2:
                break;
        }

        switch (i) {
            case 0: default: case 1: break; case 2: break;  // violation 'Default should be last'
        }

        switch (i) {
            default: case 1: break; case 2: break;  // violation 'Default should be last'
        }

        switch (i) {
            case 1: default: break; case 2: break;  // No violation with the new option is expected
        }

        switch (i) {
            case 1:
            default: // No violation with the new option is expected
                break;
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            default:  // violation 'Default should be last label in the case group.'
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
            default: // No violation with the new option is expected
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            default: // violation 'Default should be last label in the case group.'
            case 3:
                break;
            case 4:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
                break;
            default: // violation 'Default should be last label in the case group.'
            case 5:
            case 6:
                break;
        }
    }
}


