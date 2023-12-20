/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough8 {
    void methodLastLine(int i) {
        while (true) {
            switch (i) {
                case 0:
                    i++;
                    /* block */ // comment
                    // fall thru
                case 1:
                    i++;
                    break;
                case 2:
                    i++;
                    /* comment */ /* fall thru */ /* comment */
                case 3:
                    i--;
                    break;
            }
        }
    }

    void testLastCase(int i) {
        switch (i) {
            case 0:
                i++;
                /* comment */ /* fall thru */ /* comment */
        }
    }

    void testLastCase2(int i) {
        switch (i) {
            case 0:
                i++;
                /* comment */ /* comment */
                /* fall thru */

        }
    }
}
