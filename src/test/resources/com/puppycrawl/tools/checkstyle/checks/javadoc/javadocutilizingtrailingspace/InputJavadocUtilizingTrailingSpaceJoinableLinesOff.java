/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
validateOnlyJoinableLines = false
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for lines whose next line fits on them as a whole or in part.
 */
public class InputJavadocUtilizingTrailingSpaceJoinableLinesOff {

    // violation 2 lines below 'Line under-utilized (33/80). Words from below could be moved up'
    /**
     * Reads the file and returns
     * the lines of it.
     */
    public void wholeNextLineFits() { }

    // violation 2 lines below 'Line under-utilized (53/80). Words from below could be moved up'
    /**
     * Reads the file from the given path and returns
     * the lines of it to the caller as a list of strings.
     */
    public void partOfNextLineFits() { }

    // violation 2 lines below 'Line under-utilized (53/80). Words from below could be moved up'
    /**
     * Reads the file from the given path and returns
     * the lines of it as a list.
     */
    public void joinedLineIsExactlyAtLimit() { }

    // violation 2 lines below 'Line under-utilized (53/80). Words from below could be moved up'
    /**
     * Reads the file from the given path and returns
     * every line of it as a list.
     */
    public void joinedLineIsOverLimit() { }

    // violation 2 lines below 'Line under-utilized (21/80). Words from below could be moved up'
    /**
     * Reads the file
     * and returns
     * the lines of it.
     */
    public void onlyFirstLineOfChainIsReported() { }

    // violation 3 lines below 'Line under-utilized (36/80). Words from below could be moved up'
    /**
     * Reads the whole file from the given path and returns every single entry
     * of it to the caller as a list
     * of strings.
     */
    public void secondLineAfterFullLine() { }

    // violation 2 lines below 'Line under-utilized (70/80). Words from below could be moved up'
    /**
     * Reads the whole file from the given path and returns every line
     * of it to the caller as a list
     * of strings.
     */
    public void secondLineAfterLineThatFitsOneWord() { }

    // violation 2 lines below 'Line under-utilized (69/80). Words from below could be moved up'
    /**
     * @param path the path of the file to read, which is relative to
     *     the folder
     */
    public void indentationOfNextLineIsNotCounted(String path) { }

    // violation 2 lines below 'Line under-utilized (68/80). Words from below could be moved up'
    /**
     * Returns the lines of the file when it exists, otherwise it is
     * <code>null</code> or empty.
     */
    public void nextLineStartsWithCodeElement() { }

    // violation below 'Line under-utilized (26/80). Words from below could be moved up'
    /** Reads the file and
     * returns its lines.
     */
    public void openingLine() { }

    // violation 2 lines below 'Line under-utilized (25/80). Words from below could be moved up'
    /**
       Reads the file and
       returns its lines.
     */
    public void noLeadingAsterisk() { }

    // ok, the code element continues on the next line
    /**
     * See <code>someMethod(first
     *   + second)</code> and then continue.
     */
    public void multiLineCodeInSentence() { }
}
