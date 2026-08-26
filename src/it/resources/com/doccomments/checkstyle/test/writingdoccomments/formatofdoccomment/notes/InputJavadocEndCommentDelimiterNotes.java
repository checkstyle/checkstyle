package com.doccomments.checkstyle.test.writingdoccomments.formatofdoccomment.notes;

/**
 * Input for Javadoc end-comment delimiter notes examples.
 */
public class InputJavadocEndCommentDelimiterNotes {

    /**
     * Creates a sample instance.
     */
    public InputJavadocEndCommentDelimiterNotes() {
    }

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Uses a delimiter with an extra asterisk.
     **/
    public void extraAsteriskWarn() {
    }

    /**
     * Uses a delimiter with one asterisk.
     */
    public void extraAsteriskGood() {
    }

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Uses a single-line delimiter with an extra asterisk. **/
    public void singleLineExtraAsteriskWarn() {
    }

    /** Uses a single-line delimiter with one asterisk. */
    public void singleLineExtraAsteriskGood() {
    }

}
