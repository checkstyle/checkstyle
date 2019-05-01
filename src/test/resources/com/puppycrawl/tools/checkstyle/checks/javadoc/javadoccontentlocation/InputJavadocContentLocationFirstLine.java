package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

/** config: location="FIRST_LINE"
 */
public interface InputJavadocContentLocationFirstLine {

    /**
     * Text. // violation
     */
    void violation();

    /** Text. // OK
     */
    void ok();

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

    /**  **** Extra asterisks. //OK
     */
    void extraAsterisks();

    /** @implNote Does nothing. // OK
     **/
    void javadocTag();

    /** <p> // OK
     * HTML paragraph.
     * </p>
     **/
    void htmlTag();

    /** Single line. // OK **/
    void singleLine();

    /*
     * Not a javadoc comment. // OK
     */
    void notJavadocComment();

}
