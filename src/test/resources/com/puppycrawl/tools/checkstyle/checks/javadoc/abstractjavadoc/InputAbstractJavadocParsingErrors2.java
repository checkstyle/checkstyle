/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TempCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag>
 */
// violation 2 lines above 'Javadoc comment at column 4 has parse error.'
class InputAbstractJavadocParsingErrors2 {
    /**
     * <img src="singletonTag"/></img>
     */
    // violation 2 lines above 'Javadoc comment at column 35 has parse error.'
    void singletonTag() {
    }
}
