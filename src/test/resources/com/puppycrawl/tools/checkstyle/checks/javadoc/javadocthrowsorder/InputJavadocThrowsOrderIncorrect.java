/*
JavadocThrowsOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocthrowsorder;

import java.io.IOException;

public class InputJavadocThrowsOrderIncorrect {

    // violation 5 lines below """@throws tag for 'IllegalArgumentException'
    // should be placed alphabetically before 'NullPointerException'."""
    /**
     * Invalid throws order.
     * @throws NullPointerException if input is null.
     * @throws IllegalArgumentException if input is invalid.
     */
    void invalidThrows() {
    }

    // violation 5 lines below """@exception tag for 'IOException' should
    // be placed alphabetically before 'SecurityException'."""
    /**
     * Invalid exception order.
     * @exception SecurityException if security check fails.
     * @exception IOException if an I/O error occurs.
     */
    void invalidException() throws IOException {
    }

    // violation 5 lines below """@throws tag for 'IllegalArgumentException'
    // should be placed alphabetically before 'NullPointerException'."""
    /**
     * Throws and exception tags are ordered together.
     * @exception NullPointerException if input is null.
     * @throws IllegalArgumentException if input is invalid.
     */
    void invalidMixed() {
    }

    // violation 7 lines below """@throws tag for 'AlphaException' should
    // be placed alphabetically before 'ZebraException'."""
    // violation 6 lines below """@throws tag for 'BetaException' should
    // be placed alphabetically before 'ZebraException'."""
    /**
     * Later tags are compared against the greatest previous exception name.
     * @throws ZebraException if zebra fails.
     * @throws AlphaException if alpha fails.
     * @throws BetaException if beta fails.
     */
    void invalidAfterViolation()
            throws AlphaException, BetaException, ZebraException {
    }

    static class AlphaException extends Exception {
    }

    static class BetaException extends Exception {
    }

    static class ZebraException extends Exception {
    }

}
