/*
ExecutableStatementCount
max = 0
tokens = STATIC_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountStaticInit {
    // STATIC_INIT
    // violation below 'Executable statement count is 2'
    static {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }
}
