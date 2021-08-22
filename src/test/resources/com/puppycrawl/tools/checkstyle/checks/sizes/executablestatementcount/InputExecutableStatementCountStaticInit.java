/*
ExecutableStatementCount
max = 0
tokens = STATIC_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountStaticInit {
    // STATIC_INIT
    static { // violation
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }
}
