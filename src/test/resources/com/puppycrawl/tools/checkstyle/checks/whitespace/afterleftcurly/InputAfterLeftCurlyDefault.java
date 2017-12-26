package com.puppycrawl.tools.checkstyle.checks.whitespace.afterleftcurly;

/**
 * Test cases for AfterLeftCurlyCheck with default options.
 */

class InputAfterLeftCurlyDefault {

    @Annotation
    interface Interface {

        int method();
    }

    enum Enum {

        ONE,

        TWO("") {

            /**/
        };

        Enum() {
        }

        Enum(String value) {
        }
    }

    @interface Annotation {

        int[] value() default { 1, 2 };
    }
}
