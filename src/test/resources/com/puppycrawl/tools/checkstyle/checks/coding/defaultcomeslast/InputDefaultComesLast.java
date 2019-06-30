package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;


public class InputDefaultComesLast
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
        default: /**default is not last*/
            break;
        case 2:
            break;
        }

        switch (i) {
        case 1: break; default: break; case 2: break;
        }

        switch (i) {
            case 1:
            default: //violation
                break;
            case 2:
                break;
        }

        switch (i) {
            case 1:
            default: //violation
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            default: //violation
            case 1:
                break;
            case 2:
                break;
        }

        switch (i) {
            case 0: default: case 1: break; case 2: break; //violation
        }

        switch (i) {
            default: case 1: break; case 2: break; //violation
        }

        switch (i) {
            case 1: default: break; case 2: break; //violation
        }

        switch (i) {
            case 1:
            default: //violation
                break;
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            default: //violation
            case 2:
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
            default: //violation
                break;
            case 3:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
            default: //violation
            case 3:
                break;
            case 4:
                break;
        }

        switch (i) {
            default: //violation
                break;
            case 1:
                break;
        }

        switch (i) {
            case 1:
                break;
            case 2:
                break;
            default: //violation
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
