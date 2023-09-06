package org.checkstyle.suppressionxpathfilter.missingoverride;

public class SuppressionXpathRegressionMissingOverrideClass {
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) { // warn
        return false;
    }
}
