/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough6 {
    void foo(int i){
        switch(i) {
            case 1:
                 int fallthru = 2; // fallthru
            case 2:
                 int fallthrough = 2; //            fall thru
            case 3:
                 break;
            case 4:
                 int fallthru2 = 3;
                 //     fall   -   thru
            case 5: // violation 'Fall\ through from previous branch of the switch statement.'
                 int fallthru3 = 4; // This is a fallthru comment
            case 6:
                 int fallthru4 = 5; // falldown
            default: // violation 'Fall\ through from previous branch of the switch statement.'
            }
    }

     void multipleCasesOnOneLine() {
         int i = 0;
         switch (i) {
         case 0: case 1: i *= i; // fall through
         case 2: case 3: i *= i; // fall through
         case 4: case 5: i *= i; // fall through
         case 6: case 7: i *= i;
             break;
         default:
             throw new RuntimeException();
         }
     }

     void method() {
        int i=0;
        switch (i) {
            case 0:
                break;
            case 1:
                // fall through
                i++;
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
        }
     }

     void method2() {
        int i=0;
        switch (i) {
            case 0:
                break;
            case 1: // random
                // fall through
               i++;
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
        }
     }

     void method3() {
        int i=0;
        switch (i) {
            case 0:
                break;
            case 1: /* random */
                // fall through
                // non fall
             /* comment */
               i++;
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
        }
     }

     void method4() {
        int i=0;
        switch (i) {
            case 0:
                break;
            case 1: // random
                /* fallthru */
            // comment
               i++;
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
        }
     }

     void method5() {
        int i=0;
        switch (i) {
            case 0:
                break;
            case 1:
                // random
                // fall through
            // comment
                System.out.println("check");
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
        }
     }
}
