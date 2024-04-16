package org.checkstyle.suppressionxpathfilter.missingoverride;

public class InputXpathMissingOverrideInheritDocInvalidPrivateMethod {
    /**
     * {@inheritDoc}
     */
    private void test() { // warn

    }
}
