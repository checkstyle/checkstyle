/*
JavadocThrowsOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocthrowsorder;

import java.io.IOException;

public class InputJavadocThrowsOrderCorrect {

    /**
     * Valid order.
     * @throws IllegalArgumentException if input is invalid.
     * @throws NullPointerException if input is null.
     * @exception SecurityException if security check fails.
     */
    void validThrows() {
    }

    /**
     * Same exception can be repeated.
     * @throws IOException first reason.
     * @exception IOException second reason.
     */
    void duplicateInOrder() {
    }

    /**
     * No throws block tags.
     */
    void noThrows() {
    }

    /**
     * Missing throws exception name is ignored by this check.
     * @throws
     */
    void missingThrowsName() {
    }

    /**
     * Missing exception exception name is ignored by this check.
     * @exception
     */
    void missingExceptionName() {
    }

    /**
     * Valid tag after missing-name Javadocs.
     * @throws IOException if an I/O error occurs.
     */
    void validAfterMissingNames() throws IOException {
    }

    /**
     * State from this tag should not flow into the next Javadoc.
     * @throws ZebraException at the end of the alphabet.
     */
    void stateCarrier() throws ZebraException {
    }

    /**
     * This tag starts a new Javadoc tree.
     * @throws AlphaException at the beginning of the alphabet.
     */
    void stateReset() throws AlphaException {
    }

    /**
     * Split-line throws tags.
     * @throws
     *     AlphaException if alpha fails.
     * @exception
     *     BetaException if beta fails.
     */
    void splitLine() throws AlphaException, BetaException {
    }

    static class AlphaException extends Exception {
    }

    static class BetaException extends Exception {
    }

    static class ZebraException extends Exception {
    }

}
