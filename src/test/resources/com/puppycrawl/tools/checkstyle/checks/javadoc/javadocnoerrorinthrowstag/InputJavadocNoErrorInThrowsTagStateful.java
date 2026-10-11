/*
JavadocNoErrorInThrowsTag
violateExecutionOnNonTightHtml = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocnoerrorinthrowstag;

public class InputJavadocNoErrorInThrowsTagStateful {

    /**
     * Explicit Error type before another method.
     *
     * @throws Error if a serious problem occurs.
     */
    void explicitRootErrorBeforeInvalidMethod() {
        throw new Error("explicit failure");
    }

    // violation 4 lines below "Error type 'Error' should not be documented in '@throws' tag."
    /**
     * Invalid Error type after an explicit Error in the previous method.
     *
     * @throws Error if a serious problem occurs.
     */
    void invalidRootErrorAfterExplicitMethod() {
    }

    /**
     * Explicit Error in finally block after a catch may be documented.
     *
     * @throws AssertionError if cleanup fails.
     */
    void explicitErrorInFinallyWithCatch() {
        try {
            throw new RuntimeException("regular failure");
        }
        catch (RuntimeException ex) {
            return;
        }
        finally {
            throw new AssertionError("explicit failure");
        }
    }

    // violation 4 lines below "Error type 'CreateError' should not be documented in '@throws' tag."
    /**
     * Factory method throw is not a direct throw new.
     *
     * @throws CreateError if factory fails.
     */
    void invalidFactoryThrownError() {
        throw CreateError();
    }

    private CreateError CreateError() {
        return new CreateError("factory failure");
    }

    static class CreateError extends Error {
        CreateError(String message) {
            super(message);
        }
    }
}
