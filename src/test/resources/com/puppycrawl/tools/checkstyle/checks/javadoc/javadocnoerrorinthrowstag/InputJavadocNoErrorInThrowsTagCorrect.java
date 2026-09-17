/*
JavadocNoErrorInThrowsTag
violateExecutionOnNonTightHtml = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocnoerrorinthrowstag;
import java.io.IOException;

public class InputJavadocNoErrorInThrowsTagCorrect {
    /**
     * Valid checked and runtime exceptions.
     *
     * @throws IOException if an input or output exception occurs.
     * @throws IllegalArgumentException if the argument is invalid.
     * @exception NullPointerException if the argument is null.
     */
    void validExceptions() throws IOException {
    }

    /**
     * Explicit root Error type in method body may be documented.
     *
     * @throws Error if a serious problem occurs.
     */
    void explicitRootError() {
        throw new Error("explicit failure");
    }

    /**
     * Explicit Error type may be documented with a fully qualified name.
     *
     * @throws java.lang.AssertionError if an assertion fails.
     */
    void explicitQualifiedErrorTag() {
        throw new AssertionError("explicit failure");
    }

    /**
     * Explicit fully qualified Error type may be documented by simple name.
     *
     * @exception ExplicitThrownError if a custom failure occurs.
     */
    void explicitQualifiedErrorInBody() {
        throw new InputJavadocNoErrorInThrowsTagCorrect.ExplicitThrownError("explicit failure");
    }

    /**
     * Explicit Error type in constructor body may be documented.
     *
     * @throws LinkageError if linkage fails.
     */
    InputJavadocNoErrorInThrowsTagCorrect() {
        throw new LinkageError("explicit failure");
    }

    record ValidRecord(String value) {
        /**
         * Explicit Error type in compact constructor body may be documented.
         *
         * @throws AssertionError if value is invalid.
         */
        ValidRecord {
            throw new AssertionError("explicit failure");
        }
    }

    /**
     * Explicit Error type in catch block may be documented.
     *
     * @throws AssertionError if recovery fails.
     */
    void explicitErrorInCatch() {
        try {
            throw new RuntimeException("regular failure");
        }
        catch (RuntimeException ex) {
            throw new AssertionError("explicit failure");
        }
    }

    /**
     * Explicit Error type in finally block may be documented.
     *
     * @throws AssertionError if cleanup fails.
     */
    void explicitErrorInFinally() {
        try {
            return;
        }
        finally {
            throw new AssertionError("explicit failure");
        }
    }

    /**
     * Lowercase suffix does not match Java Error type naming.
     *
     * @throws Customerror if a custom exception occurs.
     */
    void lowercaseSuffix() throws Customerror {
    }

    /**
     * Missing exception name is ignored by this check.
     *
     * @throws
     */
    void missingThrowsName() {
    }

    static class Customerror extends Exception {
    }

    static class ExplicitThrownError extends Error {
        ExplicitThrownError(String message) {
            super(message);
        }
    }
}
