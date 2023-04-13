/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

import java.io.IOException;

public class InputFallThroughTryCatchInSwitch {
    public int foo(int x) {
        switch (x) {
            case 1:
                try {
                    // Some code
                    return 0;
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                    return 0;
                }

            case 2:
                try {
                    // Some code
                } catch (Exception e) {
                    // Some code
                } finally {
                    //Some code
                }
            default: // violation 'Fall through from previous branch of the switch statement.'
                // Some code
        }

        return 0;
    }
}
