/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough19 {
    void method1(int a) {
        switch (a) {}
        switch (a) {default: ; }
        switch (a) {default: {}}
        switch (a) {
            default:
        }
        switch (a) {
            default:
            {}
        }
        switch (a) {
            default:
            {
            }
        }
    }

    void method2(int a) {
        switch (a) {
            case 1:a++;
            case 2:a++; // violation 'Fall\ through from previous branch of the switch statement.'
            default: // violation 'Fall\ through from previous branch of the switch statement.'
                switch (a) {
                    default: {

                    }
                }
        }
    }

    void method3(int a, int b) {
        switch (a) {
            case 1: break;
            default: {} method2(a);
        }

        switch (b) {
            case 2: break;
            default: method2(b); {}
        }

        switch (a+b) {case 1: break; default: {} ; }
    }

    void method4(int a, int b) {
        switch (a) {
            case 1:
            default: {}
        }

        switch (b) {
            case 1:
            default:
        }

        switch (a+b) {
            default:
            case 1: { }
        }

        switch (a-b) {
            case 1:
            default: {

            } ;
            case 2: { } // violation 'Fall\ through from previous branch of the switch statement.'
        }
    }

    void method5(int a, int b) {
        switch (a) {
            case 1:
            case 2:
            case 3:
            default:
            {
            }
        }

        switch (b) {
            default:
            case 1:
            case 2: { } method2(b);
            case 3: // violation 'Fall\ through from previous branch of the switch statement.'
        }
    }
}
