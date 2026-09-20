/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlySynchronized {

    private final Object lock = new Object();

    void foo() {
        synchronized (lock) {
            lock.hashCode();}
        // violation above ''}' at column 29 should be alone on a line'
    }

    void foo1() {
        synchronized (lock) {
            lock.hashCode();
        }
    }

    void bar() {
        synchronized (lock) {}
    }

    void bar2() {
        synchronized (this) {
            lock.notify();}
        // violation above ''}' at column 27 should be alone on a line'
    }

    void bazz() {
        synchronized (lock) {
            synchronized (this) {
                lock.notifyAll();}
            // violation above ''}' at column 34 should be alone on a line'
        }
    }

    void bazz1() {
        synchronized (lock) {
            int x = 1;
            int y = 2;
            x = x + y;}
        // violation above ''}' at column 23 should be alone on a line'
    }

    void doSynchronizedFollowedByCode() {
        synchronized (lock) {
            lock.hashCode();
        }
        int after = 1;
    }

    static synchronized void doStaticSynchronized() {
        synchronized (InputGoogleRightCurlySynchronized.class) {
            System.out.println("static sync");}
        // violation above ''}' at column 47 should be alone on a line'
    }
}
