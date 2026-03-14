/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)
*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughInfiniteLoop {
    void test(int i) {
        boolean cond = true;
        switch (i) {
            case 1:
                while (true) {}
            case 2:
                for (;;) {}
            case 3:
                do {} while (true);
            case 4:
                while (cond) {}
            case 5: // violation 'Fall\ through from previous branch of the switch statement.'
                for (int j = 0; j < 10; j++) {}
            case 6: // violation 'Fall\ through from previous branch of the switch statement.'
                do {} while (cond);
            case 7: // violation 'Fall\ through from previous branch of the switch statement.'
                while (cond) {}
            case 8: // violation 'Fall\ through from previous branch of the switch statement.'
                {
                    int a = 1;
                    while (true) {}
                }
            case 9:
                for (int k : new int[]{1}) {}
            case 10: // violation 'Fall\ through from previous branch of the switch statement.'
                while (true) {}
                // single line comment
            case 11:
                while (true) {}
                // single line comment
            case 12:
                int z = 0;
            case 13: // violation 'Fall\ through from previous branch of the switch statement.'
                int b = 2;
                { }
            case 14: // violation 'Fall\ through from previous branch of the switch statement.'
                while (true) { break; }
            case 15: // violation 'Fall\ through from previous branch of the switch statement.'
                while (true) break;
            case 16: // violation 'Fall\ through from previous branch of the switch statement.'
                while (true) {}
            case 17:
                while (true) { for (;;) { break; } }
            case 18:
                while (true) { while (true) { break; } }
            case 19:
                while (true) { do { break; } while (true); }
            case 20:
                while (true) { switch (0) { case 0: break; } }
            case 21:
                while (true) { { break; } }
            case 22: // violation 'Fall\ through from previous branch of the switch statement.'
                while (true) { int y = 0; }
            case 23:
                while (true) { if (cond) {} }
            case 24:
                while (true) { if (cond) { break; } }
            case 25: // violation 'Fall\ through from previous branch of the switch statement.'
                int c = 3;
            // additional coverage cases for infinite loop detection
            case 26:
                while (true) {
                    if (cond) {
                        break;
                    }
                }
            case 27:
                while (true) {
                    while (true) {}
                }
            case 28:
                while (true) {
                    continue;
                }
            default: // violation 'Fall\ through from previous branch of the switch statement.'
                break;
        }
    }
}
