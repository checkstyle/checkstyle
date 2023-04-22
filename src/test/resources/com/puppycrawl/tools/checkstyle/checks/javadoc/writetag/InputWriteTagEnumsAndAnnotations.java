/*
WriteTag
tag = @incomplete
tagFormat = .*
tagSeverity = (default)info
tokens = ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagEnumsAndAnnotations {
    // violation 2 lines below , '@incomplete should not be used in ANNOTATION.*'
    /**
     * @incomplete This enum needs more code...
     */
    enum InputWriteTag {
        // violation 2 lines below , '@incomplete should not be used in ENUM.*'
        /**
         * @incomplete This enum constant needs more code...
         */
        FOO;
    }

    // violation 2 lines below , '@incomplete should not be used in ANNOTATION_FIELD.*'
    /**
     * @incomplete This annotation needs more code...
     */
    @interface InputWriteTag2a {
        // violation 2 lines below , '@incomplete should not be used in ENUM_CONSTANT.*'
        /**
         * @incomplete This annotation field needs more code...
         */
        int foo() default 0;
    }
}
