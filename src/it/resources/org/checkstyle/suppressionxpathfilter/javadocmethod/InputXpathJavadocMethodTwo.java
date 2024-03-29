package org.checkstyle.suppressionxpathfilter.javadocmethod;

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
