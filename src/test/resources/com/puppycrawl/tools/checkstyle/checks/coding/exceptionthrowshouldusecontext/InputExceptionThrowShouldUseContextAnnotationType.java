/*
ExceptionThrowShouldUseContext

*/

package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

public @interface InputExceptionThrowShouldUseContextAnnotationType {
    Runnable TASK = () -> {
        try {
            System.out.println("work");
        }
        catch (IllegalStateException ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new IllegalArgumentException("failed", ex);
        }
    };
}
