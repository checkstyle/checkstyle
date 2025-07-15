/*
WriteTag
tag = @incomplete
tagFormat = .*
tagSeverity = (default)info
tokens = ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagEnumsAndAnnotations {
    // violation 2 lines below 'Javadoc tag @incomplete=This enum needs more code...'
    /**
     * @incomplete This enum needs more code...
     */
    enum InputWriteTag {
        // violation 2 lines below 'Javadoc tag @incomplete=This enum constant needs more code...'
        /**
         * @incomplete This enum constant needs more code...
         */
        FOO;
    }

    // violation 2 lines below 'Javadoc tag @incomplete=This annotation needs more code...'
    /**
     * @incomplete This annotation needs more code...
     */
    @interface InputWriteTag2a {
        // violation 2 lines below '@incomplete=This annotation field needs more code...'
        /**
         * @incomplete This annotation field needs more code...
         */
        int foo() default 0;
    }
}
