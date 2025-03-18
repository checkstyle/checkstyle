package com.puppycrawl.tools.checkstyle.checks.upperell;

public class SynchronizedMethods {
    synchronized void foo() {
        synchronized (this) {} // not OK
        synchronized (Class.class) {
            synchronized (new Object()) {
                // not OK if checking statements
            }
        }
    }
}
