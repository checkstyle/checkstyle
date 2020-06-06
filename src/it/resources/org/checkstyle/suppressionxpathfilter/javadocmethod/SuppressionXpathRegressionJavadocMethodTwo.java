package org.checkstyle.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 */
public class SuppressionXpathRegressionJavadocMethodTwo {

    /**
     * Needs a param tag for x.
     *
     * @return identity
     */
    public int checkParam(int x) { //warn
        return x;
    }

}
