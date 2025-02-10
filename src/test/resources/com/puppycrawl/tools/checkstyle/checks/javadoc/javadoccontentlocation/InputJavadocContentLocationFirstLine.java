/*
JavadocContentLocation
location = first_line


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

public interface InputJavadocContentLocationFirstLine {

    /**
     * Text. // violation above 'Javadoc content should start from the same line as /\*\*.'
     */
    void violation();

    /** Text.
     */
    void ok();
    // violation below 'Javadoc content should start from the same line as /\*\*.'
    /**
     *
     * Third line.
     */
    void thirdLineViolation();

    /**
     *
     *
     */
    void blankLinesOnly();

    /**


     */
    void missingAsterisks();

    /**  **** Extra asterisks.
     */
    void extraAsterisks();

    /** @implNote Does nothing.
     **/
    void javadocTag();

    /** <p>
     * HTML paragraph.
     * </p>
     **/
    void htmlTag();

    /** Single line. **/
    void singleLine();

    /*
     * Not a javadoc comment.
     */
    void notJavadocComment();

}
