package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

/*
 * Config: default
 * tokens = { LITERAL_SYNCHRONIZED }
 */
class InputNoWhitespaceAfterTestSynchronized {
    void method2()
    {
        synchronized(this) {
        }
    }

    public void synchronized_() {
        synchronized(this) {}
        synchronized
            (this) {}
        synchronized (this) {} // violation
    }
}
