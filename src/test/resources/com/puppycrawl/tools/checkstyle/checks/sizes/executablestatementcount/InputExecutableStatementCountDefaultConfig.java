/*
ExecutableStatementCount
max = (default)30
tokens = (default)CTOR_DEF, METHOD_DEF, INSTANCE_INIT, STATIC_INIT, COMPACT_CTOR_DEF, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

public class InputExecutableStatementCountDefaultConfig {
    public void foo() { // ok
        while (true) {
            Runnable runnable = new Runnable() { // ok
                public void run() {
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    public void bar() { // ok
        if (System.currentTimeMillis() == 0) {
            if (System.currentTimeMillis() == 0 && System.currentTimeMillis() == 0) {
            }

            if (System.currentTimeMillis() == 0 || System.currentTimeMillis() == 0) {
            }
        }
    }

    public void simpleElseIf() { // ok
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    public void stupidElseIf() { // ok
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

    public InputExecutableStatementCountDefaultConfig() // ok
    {
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // STATIC_INIT
    static { // ok
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // INSTANCE_INIT
    { // ok
        int i = 1;
        if (System.currentTimeMillis() == 0) {
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    /** Inner */
    public InputExecutableStatementCountDefaultConfig(int aParam) // ok
    {
        Runnable runnable = new Runnable() {
            public void run() {// ok
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }

    /** Empty constructor */
    public InputExecutableStatementCountDefaultConfig(String someString) {} // ok

    static Runnable r1 = () -> { // ok
        String.valueOf("Hello world one!");
    };
}
