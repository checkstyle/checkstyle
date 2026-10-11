package org.checkstyle.suppressionxpathfilter.javadoc.javadocmethod;

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
