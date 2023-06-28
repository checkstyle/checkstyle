package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */
// violation below 'Unclosed HTML tag found: unclosedTag'
/**
 * <unclosedTag>
 */
class InputAbstractJavadocParsingErrors2 {
    /**
     * <img src="singletonTag"/></img>
     */
    // violation above 'Javadoc comment at column 35 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: img.'
    void singletonTag() {
    }
}
