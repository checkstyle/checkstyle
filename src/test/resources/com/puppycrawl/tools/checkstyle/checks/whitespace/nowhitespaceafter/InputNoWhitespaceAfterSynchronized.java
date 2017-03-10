package com.puppycrawl.tools.checkstyle.checks.whitespace;

class InputNoWhitespaceAfterSynchronized {
    void method2()
    {
        synchronized(this) {
        }
    }

    public void synchronzed() {
        synchronized(this) {}
        synchronized
            (this) {}
        synchronized (this) {}
    }
}