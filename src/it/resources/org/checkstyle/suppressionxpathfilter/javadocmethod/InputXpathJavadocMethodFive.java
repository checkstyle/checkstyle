package org.checkstyle.suppressionxpathfilter.javadocmethod;

/**
 * Config:
 * validateThrows = true
 */
public class InputXpathJavadocMethodFive {

    /**
     * Needs a throws tag.
     */
    public void bar() {
        throw new org.apache.tools.ant.BuildException(""); //warn
    }

}
