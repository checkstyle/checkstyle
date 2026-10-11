package org.checkstyle.suppressionxpathfilter.annotation.missingoverride;

public class InputXpathMissingOverrideInheritDocInvalidPrivateMethod {
    /**
     * {@inheritDoc}
     */
    private void test() { // warn

    }
}
