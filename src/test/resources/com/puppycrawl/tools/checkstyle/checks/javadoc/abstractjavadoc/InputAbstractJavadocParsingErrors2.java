/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

class InputAbstractJavadocParsingErrors2 {
    // violation 5 lines below 'Javadoc comment at column 5 has parse error.'
    // Details: mismatched input '<EOF>' expecting
    // 'JAVADOC_INLINE_TAG_END' while parsing INLINE_TAG
    /**
     * {@link java.util.Collections#singletonList(java.lang.Object)
     */
    void singletonTag() {

    }
}
