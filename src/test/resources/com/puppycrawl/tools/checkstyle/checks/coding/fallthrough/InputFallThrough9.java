/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough9 {

    void method5(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
                case 5: {
                    i++;
                }
                    /* block */ /* fallthru */ // comment
                case 6:
                    i++;
                    /* block */ /* fallthru */ // comment

            }
        }
    }
}
