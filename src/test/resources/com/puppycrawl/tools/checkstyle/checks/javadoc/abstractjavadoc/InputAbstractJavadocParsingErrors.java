package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

/**
 * <unclosedTag> // violation 'Unclosed HTML tag found: unclosedTag'
 */
class InputAbstractJavadocParsingErrors {
    /**
     * <img src="singletonTag"/></img> // violation 'Javadoc comment at column 35 has parse error.
     * It is forbidden to close singleton HTML tags. Tag: img.'
     */
    void singletonTag() {
    }
}
