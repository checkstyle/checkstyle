/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideInheritDocInvalidPublicMethod {
    // violation 4 lines below '{@inheritDoc} tag is not valid at this location.'
    /**
     * {@inheritDoc}
     */
    public static void test() {

    }
}
