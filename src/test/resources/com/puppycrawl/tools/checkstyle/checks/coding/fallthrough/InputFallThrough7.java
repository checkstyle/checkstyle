/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough7 {

        public void method4() {
        int c;
        switch (5) {
            case 5:
            case 4:
            case 3:
                c = 4;
            // fall through
            case 2: // violation 'Fall .* from the last branch of the switch statement'
            case 1:
            default:
                c = 9;
        }
    }

    public void method() {
        int c;
        switch (5) {
            case 5:
            case 4:
            case 3:
                c = 4;
                break;
            case 2: // violation 'Fall .* from the last branch of the switch statement'
            case 1:
            default:
                c = 9;
        }
    }

    public void method2() {
        int c;
        switch (5) {
            case 5:
            case 4:
            case 3:
                c = 4;
            case 2: // 2 violations
            case 1:
            default:
                c = 9;
        }
    }

    public void method3() {
        int c;
        switch (5) {
            case 5:
            case 4:
            case 3:
                c = 4;
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
            case 1:
            default:
                c = 9;
                break;
        }
    }

    public void method5() {
        int c;
        switch (5) {
            case 5:
            case 4:
            case 3:
                c = 4;
            // fall through
            case 2:
            case 1:
            default:
                c = 9;
                break;
        }
    }

    public void method6() {
        int c;
        switch (5) {
            case 5:
            case 4:
            case 3:
                c = 4;
                break;
            case 2:
            case 1:
            default:
                c = 9;
                break;
        }
    }

    public void method7() {
        int c;
        switch (5) {
            case 4:
            case 3:
                c = 4;
                // // fall through - sometimes get both fields
            case 2: // violation 'Fall .* from the last branch of the switch statement'
            case 1:
            default:
                c = 9;
        }
    }
}
