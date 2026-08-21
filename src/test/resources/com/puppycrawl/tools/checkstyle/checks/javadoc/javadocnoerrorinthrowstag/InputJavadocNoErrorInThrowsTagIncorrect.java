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
     * Invalid fully qualified root Error type.
     *
     * @throws java.lang.Error if a serious problem occurs.
     */
    void invalidQualifiedRootError() {
    }

    // violation 5 lines below """Error type 'OutOfMemoryError' should not be documented in
    // '@throws' tag."""
    /**
     * Invalid Error subtype.
     *
     * @throws OutOfMemoryError if memory is exhausted.
     */
    void invalidThrowsErrorSubtype() {
    }

    // violation 5 lines below """Error type 'StackOverflowError' should not be documented in
    // '@exception' tag."""
    /**
     * Invalid Error subtype in exception tag.
     *
     * @exception StackOverflowError if recursion is too deep.
     */
    void invalidExceptionErrorSubtype() {
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

}
