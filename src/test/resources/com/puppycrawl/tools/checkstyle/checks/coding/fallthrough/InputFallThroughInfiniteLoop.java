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
            case 5:
                for (int j = 0; j < 10; j++) {}
            case 6:
                do {} while (cond);
            case 7:
                while (false) {}
            case 8:
                int a = 1;
                {
                    while (true) {}
                }
            case 9:
                for (int k : new int[]{1}) {}
            case 10:
                while (true) {}
                // single line comment
            case 11:
                while (true) {}
                // single line comment
            case 12:
                int x = 0;
            case 13:
                int b = 2;
                { }
            case 14:
                while (true) { break; }
            case 15:
                while (true) break;
            case 16:

                while (true) {}
            case 17:
                while (true) { for(;;) { break; } }
            case 18:
                while (true) { while(true) { break; } }
            case 19:
                while (true) { do { break; } while(true); }
            case 20:
                while (true) { switch(0) { case 0: break; } }
            case 21:
                while (true) { { break; } }
            case 22:
                while (true) { int z = 0; }
            case 23:
                while (true) { if (cond) {} }
            case 24:
                while (true) { if (cond) { break; } }
            case 25:
                int c = 3;
            default:
                break;
        }
    }
}
