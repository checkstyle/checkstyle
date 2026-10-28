/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public interface InputXpathMissingOverrideInterface {
    // violation 4 lines below 'include @java.lang.Override annotation'
    /**
     * {@inheritDoc}
     */
    boolean test(Object obj);
}
