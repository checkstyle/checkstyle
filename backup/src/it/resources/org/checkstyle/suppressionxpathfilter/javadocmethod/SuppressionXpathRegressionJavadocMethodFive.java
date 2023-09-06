package org.checkstyle.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 * validateThrows = true
 */
public class SuppressionXpathRegressionJavadocMethodFive {

    /**
     * Needs a throws tag.
     */
    public void bar() {
        throw new org.apache.tools.ant.BuildException(""); //warn
    }

}
