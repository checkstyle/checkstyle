////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2004
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Config:
 * tag = incomplete
 * tagFormat = .*
 * severity = ignore
 * tagSeverity = error
 * tokens = ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF
 */
class InputWriteTagEnumsAndAnnotations {
    /**
     * @incomplete This enum needs more code... // violation
     */
    enum InputWriteTag {
        /**
         * @incomplete This enum constant needs more code... // violation
         */
        FOO;
    }

    /**
     * @incomplete This annotation needs more code... // violation
     */
    @interface InputWriteTag2a {
        /**
         * @incomplete This annotation field needs more code... // violation
         */
        int foo() default 0;
    }
}
