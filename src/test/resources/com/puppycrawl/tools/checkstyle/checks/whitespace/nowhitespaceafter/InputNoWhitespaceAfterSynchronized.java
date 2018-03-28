package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterSynchronized {
    void method2()
    {
        synchronized(this) {
        }
    }

    public void synchronized_() {
        synchronized(this) {}
        synchronized
            (this) {}
        synchronized (this) {}
    }
}
