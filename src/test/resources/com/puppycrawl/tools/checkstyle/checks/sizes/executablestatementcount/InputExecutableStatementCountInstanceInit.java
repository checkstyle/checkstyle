package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

/* Config:
 * max = 0
 * tokens = INSTANCE_DEF
 */

public class InputExecutableStatementCountInstanceInit {
    // INSTANCE_INIT
    { // violation
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }
}
