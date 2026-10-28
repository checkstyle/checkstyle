/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideInheritDocInvalidPrivateMethod {
    // violation 4 lines below '{@inheritDoc} tag is not valid at this location.'
    /**
     * {@inheritDoc}
     */
    private void test() {

    }
}
