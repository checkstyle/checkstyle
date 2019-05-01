package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

/**
 * config: default
 * location="SECOND_LINE"
 */
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
    void blankLinesOnly(); // violation

    /**


     */
    void missingAsterisks(); // violation

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
