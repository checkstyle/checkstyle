package org.checkstyle.checks.annotation.missingoverride;

public class InputXpathMissingOverrideClass {
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) { // warn
        return false;
    }
}
