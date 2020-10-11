//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

/*
 * Config:
 * format = "^[a-z]+$"
 */
public class InputLocalFinalVariableNameTryResourcesJava9 {
    private static final Lock LOCK = new Lock();

    public void foo() {
        LOCK.lock();
        try (LOCK) { // ok
        }
    }
}

class Lock implements AutoCloseable {

    public void lock() {
    }

    @Override
    public void close() {
    }
}
