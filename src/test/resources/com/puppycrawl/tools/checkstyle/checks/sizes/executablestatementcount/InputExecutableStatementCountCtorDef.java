/*
ExecutableStatementCount
max = 0
tokens = CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountCtorDef {
    // violation below 'Executable statement count is 2'
    public InputExecutableStatementCountCtorDef()
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // violation 2 lines below 'Executable statement count is 2'
    /** Inner */
    public InputExecutableStatementCountCtorDef(int aParam)
    {
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }

    /** Empty constructor */
    public InputExecutableStatementCountCtorDef(String someString) {}
}
