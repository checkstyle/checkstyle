package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideClass {
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) { // warn
        return false;
    }
}
