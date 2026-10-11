package org.checkstyle.suppressionxpathfilter.javadoc.javadocmethod;

/**
 * Config:
 * validateThrows = true
 */
public class InputXpathJavadocMethodFour {

    /**
     * Needs a throws tag.
     */
    public void foo() throws Exception { //warn

    }

}
