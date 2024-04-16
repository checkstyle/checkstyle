package org.checkstyle.suppressionxpathfilter.missingoverride;

public class InputXpathMissingOverrideAnonymous {
    Runnable r = new Runnable() {
        /**
         * {@inheritDoc}
         */
        public void run() { // warn

        }
    };
}
