/*
ExecutableStatementCount
max = 0
tokens = CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountCtorDef {
    public InputExecutableStatementCountCtorDef() // violation
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    /** Inner */
    public InputExecutableStatementCountCtorDef(int aParam) // violation
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
