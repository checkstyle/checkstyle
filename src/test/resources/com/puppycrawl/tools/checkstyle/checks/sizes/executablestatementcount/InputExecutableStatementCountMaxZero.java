/*
ExecutableStatementCount
max = 0
tokens = (default)CTOR_DEF,METHOD_DEF,INSTANCE_INIT,STATIC_INIT,SLIST,COMPACT_CTOR_DEF,LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountMaxZero {
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

    // violation below 'Executable statement count is 2'
    public InputExecutableStatementCountMaxZero()
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // STATIC_INIT
    // violation below 'Executable statement count is 2'
    static {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // INSTANCE_INIT
    // violation below 'Executable statement count is 2'
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // violation 2 lines below 'Executable statement count is 2'
    /** Inner */
    public InputExecutableStatementCountMaxZero(int aParam)
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

    /** Empty constructor */
    public InputExecutableStatementCountMaxZero(String someString) {}

    // violation below 'Executable statement count is 1'
    static Runnable r1 = () -> {
        String.valueOf("Hello world one!");
    };
}
