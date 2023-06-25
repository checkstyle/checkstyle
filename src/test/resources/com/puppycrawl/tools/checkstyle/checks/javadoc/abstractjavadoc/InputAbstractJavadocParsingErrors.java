/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag>
 */
// violation above 'Unclosed HTML tag found: unclosedTag'
class InputAbstractJavadocParsingErrors {
    /**
     * <img src="singletonTag"/></img>
     */
    // violation above 'Javadoc comment at column 35 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: img.'
    void singletonTag() {
    }
}
