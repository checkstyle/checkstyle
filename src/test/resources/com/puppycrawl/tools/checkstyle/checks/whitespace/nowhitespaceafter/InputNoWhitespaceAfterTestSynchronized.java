/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_SYNCHRONIZED


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

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
        synchronized (this) {} // violation, ''synchronized' is followed by whitespace.'
    }
}
