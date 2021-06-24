/*
AbstractJavadocCheckTest$Temp


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

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
