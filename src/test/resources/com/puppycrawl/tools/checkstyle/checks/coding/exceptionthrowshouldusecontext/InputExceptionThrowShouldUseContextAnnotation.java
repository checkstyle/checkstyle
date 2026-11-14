/*
ExceptionThrowShouldUseContext


*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

public @interface InputExceptionThrowShouldUseContextAnnotation {

    String NAME = "name";

    Runnable WITHOUT_CONTEXT = () -> {
        try {
            System.out.println(NAME);
        }
        catch (IllegalStateException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new IllegalArgumentException("failed", ex);
        }
    };

    Runnable WITH_CONTEXT = () -> {
        try {
            System.out.println(NAME);
        }
        catch (IllegalStateException ex) {
            // ok - uses 'NAME'
            throw new IllegalArgumentException(NAME, ex);
        }
    };
}
