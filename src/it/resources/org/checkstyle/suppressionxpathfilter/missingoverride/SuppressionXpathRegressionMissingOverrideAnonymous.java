package org.checkstyle.suppressionxpathfilter.missingoverride;

public class SuppressionXpathRegressionMissingOverrideAnonymous {
    Runnable r = new Runnable() {
        /**
         * {@inheritDoc}
         */
        public void run() { // warn

        }
    };
}
