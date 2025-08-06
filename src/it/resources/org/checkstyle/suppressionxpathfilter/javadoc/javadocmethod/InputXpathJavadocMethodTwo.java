package org.checkstyle.suppressionxpathfilter.javadoc.javadocmethod;

/**
 * Config:
 */
public class InputXpathJavadocMethodTwo {

    /**
     * Needs a param tag for x.
     *
     * @return identity
     */
    public int checkParam(int x) { //warn
        return x;
    }

}
