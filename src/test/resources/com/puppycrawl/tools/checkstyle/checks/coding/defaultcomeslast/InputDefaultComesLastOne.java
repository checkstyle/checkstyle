/*
DefaultComesLast
skipIfLastAndSharedWithCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public class InputDefaultComesLastOne
{
    void method(int i) {
        // switch with last default
        switch (i) {
        case 1: break;
        case 2: break;
        default:
            // do something :)
        }

        // switch w/o default (not a problem)
        switch (i) {
        case 1: break;
        case 2: break;
        }

        // VIOLATION!!! default is not the last one.
        switch (i) {
        case 1:
            break;
        default: /**default is not last*/ // violation 'Default should be last label in the switch.'
            break;
        case 2:
            break;
        }

        switch (i) {
        case 1: break; default: break; case 2: break; // violation 'Default should be last.'
        }

        switch (i) {
            case 1:
            default: // violation 'Default should be last label in the switch.'
                break;
            case 2:
                break;
        }

        switch (i) {
            case 1:
            default: // violation 'Default should be last label in the switch.'
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            default: // violation 'Default should be last label in the switch.'
            case 1:
                break;
            case 2:
                break;
        }

        switch (i) {
            case 0: default: case 1: break; case 2: break; // violation 'Default should be last.'
        }

        switch (i) {
            default: case 1: break; case 2: break; // violation 'Default should be last.'
        }

        switch (i) {
            case 1: default: break; case 2: break; // violation 'Default should be last.'
        }

        switch (i) {
            case 1:
            default: // violation 'Default should be last label in the switch.'
                break;
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            default: // violation 'Default should be last label in the switch.'
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
            default: // violation 'Default should be last label in the switch.'
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
            default: // violation 'Default should be last label in the switch.'
            case 3:
                break;
            case 4:
                break;
        }
    }
}

