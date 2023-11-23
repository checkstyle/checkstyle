/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TempCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag>
 */
// violation 2 lines above 'Javadoc comment at column 4 has parse error.'
// Missed HTML close tag 'unclosedTag'. Sometimes it means that close tag missed for
// one of previous tags.
class InputAbstractJavadocParsingErrors {
    /**
     * <img src="singletonTag"/></img>
     */
    // violation 2 lines above 'Javadoc comment at column 35 has parse error.'
    // It is forbidden to close singleton HTML tags. Tag: img.
    void singletonTag() {
    }
}
