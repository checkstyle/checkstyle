/*
JavadocContentLocation
location = (defaut)SECOND_LINE


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

public interface InputJavadocContentLocationDefault {

    /**
     * Text. // OK
     */
    void ok();

    /** Text. // violation
     */
    void violation();

    /**
     *
     * Third line. // violation
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
      Text. //OK
      More text.
     */
    void missingAsterisksWithText();

    /**
      **** Extra asterisks. //OK
      */
    void extraAsterisks();

    /**
     * @implNote Does nothing. // OK
     **/
    void javadocTag();

    /**
     * <p> // OK
     * HTML paragraph.
     * </p>
     **/
    void htmlTag();

    /** Single line. **/ // OK
    void singleLine();

    /***/ // OK
    void emptyJavadocComment();

    /**/ // OK
    void emptyComment();

    /* Not a javadoc comment. // OK
    */
    void notJavadocComment();

}
