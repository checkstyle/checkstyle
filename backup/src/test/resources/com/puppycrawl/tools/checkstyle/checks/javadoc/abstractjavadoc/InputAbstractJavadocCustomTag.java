package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

public class InputAbstractJavadocCustomTag {
    /**
     * {@customTag} // ok
     */
    void customTag() {}

    /** {@customTag} */ // ok
    void customTag2() {}
}
