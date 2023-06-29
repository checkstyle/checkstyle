package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

/**
 * <unclosedTag>
 */
// violation above 'Unclosed HTML tag found: unclosedTag'
class InputAbstractJavadocParsingErrors2 {
    /**
     * <img src="singletonTag"/></img>
     */
    // violation above 'Javadoc comment at column 35 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: img.'
    void singletonTag() {
    }
}
