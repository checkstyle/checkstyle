package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

/**
 * <unclosedTag> // violation
 */
class InputAbstractJavadocParsingErrors2 {
    /**
     * <img src="singletonTag"/></img> // violation
     */
    void singletonTag() {
    }
}
