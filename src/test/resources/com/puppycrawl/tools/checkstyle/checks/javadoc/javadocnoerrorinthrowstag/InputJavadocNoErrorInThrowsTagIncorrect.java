/*
JavadocNoErrorInThrowsTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocnoerrorinthrowstag;

public class InputJavadocNoErrorInThrowsTagIncorrect {

    // violation 4 lines below "Error type 'Error' should not be documented in '@throws' tag."
    /**
     * Invalid root Error type.
     *
     * @throws Error if a serious problem occurs.
     */
    void invalidRootError() {
    }

    // violation 5 lines below """Error type 'java.lang.Error' should not be documented in
    // '@throws' tag."""
    /**
     * Invalid fully qualified root Error type without matching thrown Error.
     *
     * @throws java.lang.Error if a serious problem occurs.
     */
    void invalidQualifiedRootError() {
        throw new AssertionError("different failure");
    }

    // violation 5 lines below """Error type 'OutOfMemoryError' should not be documented in
    // '@throws' tag."""
    /**
     * Invalid caught Error subtype.
     *
     * @throws OutOfMemoryError if memory is exhausted.
     */
    void invalidCaughtErrorSubtype() {
        try {
            throw new OutOfMemoryError("caught failure");
        }
        catch (OutOfMemoryError ex) {
            // ignore
        }
    }

    // violation 5 lines below """Error type 'StackOverflowError' should not be documented in
    // '@exception' tag."""
    /**
     * Invalid Error subtype thrown only in lambda body.
     *
     * @exception StackOverflowError if recursion is too deep.
     */
    void invalidLambdaErrorSubtype() {
        final Runnable runnable = () -> {
            throw new StackOverflowError("nested failure");
        };
    }

    // violation 5 lines below """Error type 'com.example.CustomError' should not be documented
    // in '@throws' tag."""
    /**
     * Invalid custom Error-like name.
     *
     * @throws com.example.CustomError if a custom failure occurs.
     */
    void invalidCustomErrorName() {
    }

    record InvalidRecord(String value) {

        // violation 5 lines below """Error type 'AssertionError' should not be documented in
        // '@throws' tag."""
        /**
         * Invalid Error type in compact constructor.
         *
         * @throws AssertionError if value is invalid.
         */
        InvalidRecord {
        }

    }

    // violation 4 lines below "Error type 'Error' should not be documented in '@throws' tag."
    /**
     * Invalid Error type thrown through a variable.
     *
     * @throws Error if a serious problem occurs.
     */
    void invalidErrorThrownByVariable() {
        final Error error = new Error("indirect failure");
        throw error;
    }

    // violation 4 lines below "Error type 'Error' should not be documented in '@throws' tag."
    /**
     * Invalid documented Error type with a different thrown type.
     *
     * @throws Error if a serious problem occurs.
     */
    void invalidDifferentThrownType() {
        throw new RuntimeException("different failure");
    }

    // violation 4 lines below "Error type 'Error' should not be documented in '@throws' tag."
    /**
     * Invalid Error type thrown only in an anonymous class body.
     *
     * @throws Error if a serious problem occurs.
     */
    void invalidAnonymousClassError() {
        new Runnable() {
            @Override
            public void run() {
                throw new Error("nested failure");
            }
        };
    }
}
