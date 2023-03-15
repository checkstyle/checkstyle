package org.checkstyle.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 */
public class SuppressionXpathRegressionJavadocMethodOne {

    /** {@inheritDoc} */
    public void inheritableMethod() {
        // may be inherited and overridden
    }

    /** {@inheritDoc} */
    private void uninheritableMethod() { //warn
        // not possible to be inherited
    }

}
