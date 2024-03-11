package org.checkstyle.suppressionxpathfilter.missingoverride;

public class InputXpathMissingOverrideClass {
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) { // warn
        return false;
    }
}
