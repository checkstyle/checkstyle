/*
MissingOverride
javaFiveCompatibility = true


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideClassJavaFive {
    // violation 4 lines below 'include @java.lang.Override annotation'
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {
        return false;
    }
}
