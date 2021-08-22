/*
ExecutableStatementCount
max = 0
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountMethodDef {
    public void foo() { // violation
        while (true) {
            Runnable runnable = new Runnable() {
                public void run() { // violation
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    public void bar() { // violation
        if (System.currentTimeMillis() == 0) {
            if (System.currentTimeMillis() == 0 && System.currentTimeMillis() == 0) {
            }

            if (System.currentTimeMillis() == 0 || System.currentTimeMillis() == 0) {
            }
        }
    }

    public void simpleElseIf() { // violation
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    public void stupidElseIf() { // violation
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
            public void run() { // violation
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }
}
