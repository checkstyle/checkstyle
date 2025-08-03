package org.checkstyle.checks.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 */
public class InputXpathJavadocMethodOne {

    /** {@inheritDoc} */
    public void inheritableMethod() {
        // may be inherited and overridden
    }

    /** {@inheritDoc} */
    private void uninheritableMethod() { //warn
        // not possible to be inherited
    }

}
