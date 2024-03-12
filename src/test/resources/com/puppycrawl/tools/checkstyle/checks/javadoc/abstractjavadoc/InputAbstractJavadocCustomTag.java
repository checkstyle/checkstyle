/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

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
