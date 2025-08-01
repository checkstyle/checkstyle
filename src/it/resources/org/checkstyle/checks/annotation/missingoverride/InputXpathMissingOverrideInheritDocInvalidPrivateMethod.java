package org.checkstyle.checks.annotation.missingoverride;

public class InputXpathMissingOverrideInheritDocInvalidPrivateMethod {
    /**
     * {@inheritDoc}
     */
    private void test() { // warn

    }
}
