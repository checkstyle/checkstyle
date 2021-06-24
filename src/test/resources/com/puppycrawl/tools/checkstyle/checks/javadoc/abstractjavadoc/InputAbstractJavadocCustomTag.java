/*
AbstractJavadocCheckTest$Temp


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

public class InputAbstractJavadocCustomTag {
    /**
     * {@customTag} // ok
     */
    void customTag() {}

    /** {@customTag} */ // ok
    void customTag2() {}
}
