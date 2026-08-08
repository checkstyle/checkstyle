/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughFiniteLoop {
    void method(int a, int b) {
        switch (a) {
            case 1:
                while (b < 10) {
                    b++;
                }
            case 2: // violation 'Fall .* from previous branch of the switch statement'
                b++;
                break;
            case 3:
                for (int i = 0; i < 10; i++) {
                    b++;
                }
            case 4: // violation 'Fall .* from previous branch of the switch statement'
                b++;
            case 5: // violation 'Fall .* from previous branch of the switch statement'
                break;
            case 6:
                do {
                    b++;
                } while (b < 10);
            case 7: // violation 'Fall .* from previous branch of the switch statement'
                break;
            case 8:
                for (int i = 0; i < 12; i++) {
                    b++;
                }
            case 9: // violation 'Fall .* from previous branch of the switch statement'
                break;
            case 10:
                while (a < b) {
                    if (a == b) {
                        return;
                    }
                }
            case 11:  // violation 'Fall .* from previous branch of the switch statement'
                break;
            default:
                break;
        }
    }

    void method2(int a, int b) {
        switch (a) {
            case 1:
                while (true) {
                    b++;
                    break;
                }
            case 2: // violation 'Fall .* from previous branch of the switch statement'
                b++;
                break;
            case 3:
                for (;;) {
                    b++;
                    break;
                }
            case 4: // violation 'Fall .* from previous branch of the switch statement'
                b++;
            case 5: // violation 'Fall .* from previous branch of the switch statement'
                break;
            case 6:
                do {
                    b++;
                    break;
                } while (true);
            case 7: // violation 'Fall .* from previous branch of the switch statement'
                break;
            case 8:
                for (;;) break;
            case 9: // violation 'Fall .* from previous branch of the switch statement'
                break;
            case 10:
                while (true) break;
            case 11: // violation 'Fall .* from previous branch of the switch statement'
                b--;
                break;
            case 12:
                do {
                    if (a == 12) break;
                } while (true);
            case 13: // violation 'Fall .* from previous branch of the switch statement'
                b++;
                break;
            default:
                break;
        }
    }

    void method3(int a) {
        int[] arr = {1, 2, 3};
        switch (a) {
            case 1:
                while (true) {
                    if (a == 2) {
                        continue;
                    } else {
                        break;
                    }
                }
            case 2: // violation 'Fall .* from previous branch of the switch statement'
               for (int n : arr) {
                   n++;
               }
            default: // violation 'Fall .* from previous branch of the switch statement'
        }
    }
}
