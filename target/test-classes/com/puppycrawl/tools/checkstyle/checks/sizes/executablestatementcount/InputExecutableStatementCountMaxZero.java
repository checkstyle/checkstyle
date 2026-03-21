/*
ExecutableStatementCount
max = 0
tokens = (default)CTOR_DEF, METHOD_DEF, INSTANCE_INIT, STATIC_INIT, COMPACT_CTOR_DEF, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountMaxZero {
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

    public InputExecutableStatementCountMaxZero() // violation
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // STATIC_INIT
    static { // violation
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // INSTANCE_INIT
    { // violation
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    /** Inner */
    public InputExecutableStatementCountMaxZero(int aParam) // violation
    {
        Runnable runnable = new Runnable() {
            public void run() { // violation
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }

    /** Empty constructor */
    public InputExecutableStatementCountMaxZero(String someString) {}

    static Runnable r1 = () -> { // violation
        String.valueOf("Hello world one!");
    };
}
