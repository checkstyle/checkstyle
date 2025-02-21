/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_SYNCHRONIZED


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputNoWhitespaceAfterSynchronized<T> {

    private final Object lock = new Object();

    void m1() {
        synchronized (lock) {} // violation
        synchronized(lock) {}
    }

    synchronized int m2() {
        return 2;
    }

    synchronized private int[] m3() {
        return new int[]{2};
    }

    synchronized int[] m4() {
        return new int[]{2};
    }

    private final synchronized int m5() {
        return 2;
    }

    synchronized T m6() {
        return null;
    }

    @SyncAnno synchronized T m7() {
        return null;
    }

    @SyncAnno private synchronized T m8() {
        return null;
    }

    // Note that we do not check 'synchronized' as a modifier,
    // convention is to have type parameters of a generic method
    // be surrounded by whitespace.
    synchronized <T2> void m9() {}
    synchronized<T2> void m10() {}
}

@Target(ElementType.METHOD)
@interface SyncAnno {
}
