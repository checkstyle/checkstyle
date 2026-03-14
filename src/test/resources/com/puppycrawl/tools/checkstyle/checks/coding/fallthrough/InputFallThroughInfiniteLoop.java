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
                while (cond) { }
            case 2:
                for (; cond; ) { }
            case 3:
                do { } while (cond);
            case 4:
                while (cond) { }
            case 5: // violation 'Fall\ through from previous branch of the switch statement.'
                { int a = 1; }
            case 6: // violation 'Fall\ through from previous branch of the switch statement.'
                { int b = 1; }
            case 7: // violation 'Fall\ through from previous branch of the switch statement.'
                while (false) { }
            case 8: // violation 'Fall\ through from previous branch of the switch statement.'
                {
                    while (cond) { }
                }
            case 9:
                for (int k : new int[]{1}) { }
            case 10: // violation 'Fall\ through from previous branch of the switch statement.'
                while (cond) { }
            case 11:
                while (cond) { }
            case 12:
                { int z = 0; }
            case 13: // violation 'Fall\ through from previous branch of the switch statement.'
                { int c = 2; }
            case 14: // violation 'Fall\ through from previous branch of the switch statement.'
                while (cond) { break; }
            case 15: // violation 'Fall\ through from previous branch of the switch statement.'
                while (cond) break;
            case 16: // violation 'Fall\ through from previous branch of the switch statement.'
                while (cond) { }
            case 17:
                while (cond) { for(;cond;) { break; } }
            case 18:
                while (cond) { while(cond) { break; } }
            case 19:
                while (cond) { do { break; } while(cond); }
            case 20:
                while (cond) { switch(0) { case 0: break; } }
            case 21:
                while (cond) { { break; } }
            case 22: // violation 'Fall\ through from previous branch of the switch statement.'
                while (cond) { int y = 0; }
            case 23:
                while (cond) { if (cond) { } }
            case 24:
                while (cond) { if (cond) { break; } }
            case 25: // violation 'Fall\ through from previous branch of the switch statement.'
                { int d = 3; }
            default: // violation 'Fall\ through from previous branch of the switch statement.'
                break;
        }
    }
}
