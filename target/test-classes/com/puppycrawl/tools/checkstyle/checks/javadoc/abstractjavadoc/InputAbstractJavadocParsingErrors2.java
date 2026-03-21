/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

class InputAbstractJavadocParsingErrors2 {
    /**
     * {@link java.util.Collections#singletonList(java.lang.Object)
     */
    // violation above 'Javadoc comment at column 5 has parse error.'
    // Details: mismatched input '<EOF>' expecting
    // 'JAVADOC_INLINE_TAG_END' while parsing INLINE_TAG
    void singletonTag() {

    }
}
