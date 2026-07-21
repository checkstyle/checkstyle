/*
ExecutableStatementCount
max = 0
tokens = INSTANCE_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountInstanceInit {
    // INSTANCE_INIT
    // violation below 'Executable statement count is 2'
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }
}
