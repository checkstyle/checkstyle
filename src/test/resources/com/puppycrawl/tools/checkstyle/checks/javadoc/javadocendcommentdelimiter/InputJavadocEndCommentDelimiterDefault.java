/*
JavadocEndCommentDelimiter

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocendcommentdelimiter;

public class InputJavadocEndCommentDelimiterDefault {

    /** Valid Javadoc. */
    public void validSingleLine() { }

    /**
     * Valid Javadoc.
     */
    public void validMultiline() { }

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Invalid single-line Javadoc. **/
    public void invalidSingleLine() { }

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Invalid Javadoc.
     ***/
    public void invalidMultiline() { }

    /** Valid because the extra asterisk is separated. * */
    public void separatedAsterisk() { }

    /* Not a Javadoc comment. **/
    public void blockComment() { }

    /**/
    public void emptyComment() { }

}
