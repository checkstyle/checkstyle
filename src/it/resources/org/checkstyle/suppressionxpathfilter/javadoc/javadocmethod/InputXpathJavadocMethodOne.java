package org.checkstyle.suppressionxpathfilter.javadoc.javadocmethod;

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
