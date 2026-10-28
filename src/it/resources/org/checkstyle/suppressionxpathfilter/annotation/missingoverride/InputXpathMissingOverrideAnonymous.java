/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideAnonymous {
    Runnable r = new Runnable() {
        // violation 4 lines below 'include @java.lang.Override annotation'
        /**
         * {@inheritDoc}
         */
        public void run() {

        }
    };
}
