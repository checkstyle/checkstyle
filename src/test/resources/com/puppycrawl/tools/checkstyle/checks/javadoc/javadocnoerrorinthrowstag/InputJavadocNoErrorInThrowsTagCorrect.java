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

}
