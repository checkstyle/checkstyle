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
    void blankLinesOnly(); // OK

    /**


     */
    void missingAsterisks(); // OK

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
    void emptyComment();

}
