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
        synchronized(lock) {} // ok
    }

    synchronized int m2() { // ok
        return 2;
    }

    synchronized private int[] m3() { // ok
        return new int[]{2};
    }

    synchronized int[] m4() { // ok
        return new int[]{2};
    }

    private final synchronized int m5() { // ok
        return 2;
    }

    synchronized T m6() { // ok
        return null;
    }

    @SyncAnno synchronized T m7() { // ok
        return null;
    }

    @SyncAnno private synchronized T m8() { // ok
        return null;
    }

    // Note that we do not check 'synchronized' as a modifier,
    // convention is to have type parameters of a generic method
    // be surrounded by whitespace.
    synchronized <T2> void m9() {} // ok
    synchronized<T2> void m10() {} // ok
}

@Target(ElementType.METHOD)
@interface SyncAnno {
}
