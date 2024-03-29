package org.checkstyle.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 */
public class InputXpathJavadocMethodThree {

    /**
     * Needs a param tag for T.
     *
     * @param x the input
     * @return hashcode of x
     */
    public <T> int checkTypeParam(T x) { //warn
        return x.hashCode();
    }

}
