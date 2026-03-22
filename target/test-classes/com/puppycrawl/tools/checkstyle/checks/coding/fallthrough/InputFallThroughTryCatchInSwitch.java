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
                    throw new IOException("Exception occurred.");
                } catch (IOException e) {
                    // Some code
                    if (e.getMessage().contains("Exception")) {
                        break;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    // Some code
                    for (int i = 0; i < 3; i++) {
                        if (i == 1) {
                            break;
                        }
                    }
                } finally {
                    // Some code
                }
            default: // violation 'Fall\ through from previous branch of the switch statement.'
                // Some code
        }

        return 0;
    }
}
