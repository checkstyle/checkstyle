/*
WriteTag
tag = @incomplete
tagFormat = .*
tokens = ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagEnumsAndAnnotations {
    /**
     * @incomplete This enum needs more code...
     */
    enum InputWriteTag {
        /**
         * @incomplete This enum constant needs more code...
         */
        FOO;
    }

    /**
     * @incomplete This annotation needs more code...
     */
    @interface InputWriteTag2a {
        /**
         * @incomplete This annotation field needs more code...
         */
        int foo() default 0;
    }
}
