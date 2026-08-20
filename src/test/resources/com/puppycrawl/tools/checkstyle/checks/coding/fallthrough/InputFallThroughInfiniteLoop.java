/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughInfiniteLoop {
    void method(int i) {
        switch (i) {
            case 1:
                for (;;) {}
            case 2:
                while (true) {}
            case 3:
                do {
                } while (true);
            case 4:
                while (true) {
                    i++;
                    continue;
                }
            case 5:
                i = 2;
                break;
            default:
                break;
        }
    }

    void method2(int i) {
        switch (i) {
            case 1:
                for (int j = 0; j < 3; j++) {
                    foo();
                }

                for (int j : new int[] {1, 2, 3}) {
                    foo(j);
                }

                while (true) {
                    foo(1, 2);
                }
            case 2:
                while (true) {
                    foo(1, 2);
                }
            case 3:
                int k = 1;
                while (k < 2) {
                    foo(k);
                    k++;
                }
                do foo(); while (true);
            case 4:
                 int r = 2;
                 for(; true;) {
                     foo(r);
                 }
            case 5:
                foo(1);
        }
    }


    void foo(int... a) {}
}
