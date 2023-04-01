/*
WriteTag
tag = @incomplete
tagFormat = .*
tagSeverity = error
tokens = ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 3, 7, 13, 17 lines below , 'Cannot be empty.*'
class InputWriteTagEnumsAndAnnotations {
    /**
     * @incomplete This enum needs more code... // violation 'Cannot be empty.'
     */
    enum InputWriteTag {
        /**
         * @incomplete This enum constant needs more code... // violation 'Cannot be empty.'
         */
        FOO;
    }

    /**
     * @incomplete This annotation needs more code... // violation 'Cannot be empty.'
     */
    @interface InputWriteTag2a {
        /**
         * @incomplete This annotation field needs more code... // violation 'Cannot be empty.'
         */
        int foo() default 0;
    }
}
