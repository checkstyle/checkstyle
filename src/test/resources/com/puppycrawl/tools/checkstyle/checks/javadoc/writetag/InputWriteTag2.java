////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2004
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * @incomplete This enum needs more code...
 */
enum InputWriteTag2 {
    /**
     * @incomplete This enum constant needs more code...
     */
    FOO;
}

/**
 * @incomplete This annotation needs more code...
 */
@interface InputWriteTag3 {
    /**
     * @incomplete This annotation field needs more code...
     */
    int foo() default 0;
}
