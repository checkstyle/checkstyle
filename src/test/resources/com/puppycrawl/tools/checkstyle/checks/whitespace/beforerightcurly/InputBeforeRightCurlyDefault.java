package com.puppycrawl.tools.checkstyle.checks.whitespace.beforerightcurly;

/**
 * Test cases for BeforeRightCurlyCheck with default options.
 */

class InputBeforeRightCurlyDefault {

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
