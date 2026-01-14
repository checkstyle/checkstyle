/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for lines that exceed the configured limit (too long).
 */
public class InputJavadocUtilizingTrailingSpaceTooLong {

    // violation 2 lines below 'Line is longer than 80 characters (found 108).'
    /**
     * This is a very long line that definitely exceeds the eighty character limit that has been configured.
     */
    public void singleLongLine() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 116).'
    /**
     * This description starts off reasonably but then it goes on and on and on and exceeds the limit significantly.
     */
    public void longDescription() { }

    // violation 3 lines below 'Line is smaller than 80 characters (found 19).'
    // violation 3 lines below 'Line is longer than 80 characters (found 121).'
    /**
     * Normal line.
     * But this second line is extremely long and will definitely trigger a too long violation because it exceeds eighty.
     */
    public void secondLineTooLong() { }

    /**
     * @param value a parameter that has an extremely long description that goes well beyond the eighty character limit here
     */
    public void paramTooLong(int value) { }

    /**
     * @return returns a value with a description that is way too long and exceeds the configured line length limit significantly
     */
    public int returnTooLong() {
        return 0;
    }

    /**
     * @throws Exception when something goes terribly wrong and we need a very long explanation that exceeds the limit
     */
    public void throwsTooLong() throws Exception { }

    /**
     * This is a line that is exactly eighty characters long including spaces he
     * word
     */
    public void exactlyEightyCharacters() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 92).'
    /**
     * This is a line that is exactly eighty characters long including spaces here and there
     * word
     */
    public void moreEightyChars() { }

}
