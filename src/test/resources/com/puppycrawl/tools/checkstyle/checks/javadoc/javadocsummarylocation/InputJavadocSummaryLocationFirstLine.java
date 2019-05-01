package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocsummarylocation;

/** config: location="FIRST_LINE"
 */
public interface InputJavadocSummaryLocationFirstLine {

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
    void blankLinesOnly(); // OK

    /**


     */
    void missingAsterisks(); // OK

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

}
