/*
JavadocContentLocation
location = \tFIRST_LINE


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

public interface InputJavadocContentLocationTrimOptionProperty {

    // violation below 'Javadoc content should start from the same line as /\*\*.'
    /**
     * Text.
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

}
