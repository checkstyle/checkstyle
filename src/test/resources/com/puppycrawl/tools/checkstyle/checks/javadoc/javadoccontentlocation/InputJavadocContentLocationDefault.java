/*
JavadocContentLocation
location = (default)SECOND_LINE


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

public interface InputJavadocContentLocationDefault {

    /**
     * Text.
     */
    void ok();

    // violation below 'Javadoc content should start from the next line after /\*\*.'
    /** Text.
     */
    void violation();

    // violation below 'Javadoc content should start from the next line after /\*\*.'
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

    /**
      Text.
      More text.
     */
    void missingAsterisksWithText();

    /**
      **** Extra asterisks.
      */
    void extraAsterisks();

    /**
     * @implNote Does nothing.
     **/
    void javadocTag();

    /**
     * <p>
     * HTML paragraph.
     * </p>
     **/
    void htmlTag();

    /** Single line. **/
    void singleLine();

    /***/
    void emptyJavadocComment();

    /**/
    void emptyComment();

    /* Not a javadoc comment.
    */
    void notJavadocComment();

}
