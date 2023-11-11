/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough10 {

      void method5(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
                case 5:
                    i++;
                    /* block */ /* Fallthru */ // comment
                case 6: // violation 'Fall\ through from previous branch of the switch statement.'
                    i++;
                    /* block */ /* fallthru */ // comment

            }
        }
    }
}
