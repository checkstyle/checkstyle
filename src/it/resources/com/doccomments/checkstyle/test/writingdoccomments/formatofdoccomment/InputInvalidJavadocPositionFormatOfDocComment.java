package com.doccomments.checkstyle.test.writingdoccomments.formatofdoccomment;

/**
 * Input with a Javadoc comment in an invalid location.
 */
public class InputInvalidJavadocPositionFormatOfDocComment {

    /**
     * Creates a sample instance.
     */
    public InputInvalidJavadocPositionFormatOfDocComment() {
    }

    /**
     * Runs the sample.
     */
    public void run() {
        // violation below 'Javadoc comment is placed in the wrong location.'
        /**
         * This Javadoc comment is inside a method body.
         */
        int value = 1;
        value++;
    }

}
