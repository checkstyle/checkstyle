/*
ExecutableStatementCount
max = 0
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountMethodDef {
    // violation below 'Executable statement count is 3'
    public void foo() {
        while (true) {
            Runnable runnable = new Runnable() {
                // violation below 'Executable statement count is 1'
                public void run() {
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    // violation below 'Executable statement count is 2'
    public void bar() {
        if (System.currentTimeMillis() == 0) {
            if (System.currentTimeMillis() == 0 && System.currentTimeMillis() == 0) {
            }

            if (System.currentTimeMillis() == 0 || System.currentTimeMillis() == 0) {
            }
        }
    }

    // violation below 'Executable statement count is 1'
    public void simpleElseIf() {
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // violation below 'Executable statement count is 3'
    public void stupidElseIf() {
        if (System.currentTimeMillis() == 0) {
        } else {
            if (System.currentTimeMillis() == 0) {
            } else {
                if (System.currentTimeMillis() == 0) {
                }
            }

            if (System.currentTimeMillis() == 0) {
            }
        }
    }

    /** Inner */
    public InputExecutableStatementCountMethodDef(int aParam)
    {
        Runnable runnable = new Runnable() {
            // violation below 'Executable statement count is 1'
            public void run() {
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }
}
