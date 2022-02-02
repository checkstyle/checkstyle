/*
LocalFinalVariableName
format = [a-z]+
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

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
