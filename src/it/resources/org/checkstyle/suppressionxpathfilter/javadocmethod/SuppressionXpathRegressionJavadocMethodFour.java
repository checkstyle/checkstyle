package org.checkstyle.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 * validateThrows = true
 */
public class SuppressionXpathRegressionJavadocMethodFour {

    /**
     * Needs a throws tag.
     */
    public void foo() throws Exception { //warn

    }

}
