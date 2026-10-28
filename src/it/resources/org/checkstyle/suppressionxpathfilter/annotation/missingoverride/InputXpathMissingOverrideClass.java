/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideClass {
    // violation 4 lines below 'include @java.lang.Override annotation'
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {
        return false;
    }
}
