/*
MultilineCommentLeadingAsteriskPresence


*/
// violation 5 lines above 'Line in block comment should start with '*''

package com.puppycrawl.tools.checkstyle.checks.whitespace.multilinecommentleadingasteriskpresence;

/** Test input for MultilineCommentLeadingAsteriskPresenceCheck */
public class InputMultilineCommentLeadingAsteriskPresence {
    /* // violation 'Line in block comment should start with '*''
     Missing asterisk on this line.
     */
    public void singleLineMissing() {
    }

    // violation below 'Line in block comment should start with '*''
    /*
     First line missing asterisk.
     * Second line has asterisk.
     Third line missing asterisk.
     */
    public void multipleMissing() {
    }

    // violation below 'Line in block comment should start with '*''
    /*
     * First line correct.
     Second line missing.
     * Third line correct.
     */
    public void mixedFormat() {
    }

    /* Single line comment is OK */
    public void singleLineOk() {
    }

    /**
     * Javadoc comment should be ignored.
     Even without asterisk, no violation here.
     */
    public void javadocIgnored() {
    }

    /*
     * Correct format with empty line.
     *
     * After empty line.
     */
    public void withEmptyLine() {
    }

    // violation below 'Line in block comment should start with '*''
    /*
     * Line with asterisk.

     Empty line without asterisk.
     */
    public void emptyLineMissing() {
    }

    /*
     * All lines correct.
     * Including this one.
     * And this one too.
     */
    public void allCorrect() {
    }

    // violation below 'Line in block comment should start with '*''
    /*
     All wrong.
     Every line.
     Missing asterisks.
     */
    public void allWrong() {
    }

    // violation below 'Line in block comment should start with '*''
    /*
     * Correct start.
     Indented without asterisk.
     */
    public void indentedMissing() {
    }

    /* Inline comment */ public void inlineOk() { }

    /* // violation 'Line in block comment should start with '*''
      Leading spaces before text.
     */
    public void spacesBeforeText() {
    }
}
