package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

/**
 * <unclosedTag> // violation
 */
class InputAbstractJavadocParsingErrors {
    /**
     * <img src="singletonTag"/></img> // violation
     */
    void singletonTag() {
    }
}
