/*
JavadocContentLocation
location = FIRST_LINE


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

public interface InputJavadocContentLocationFirstLine {

    /**
     * Text. // violation above
     */
    void violation();

    /** Text. // OK
     */
    void ok();
    // violation below
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
