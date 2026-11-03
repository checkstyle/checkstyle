/*
JavadocContentLocation
location = first_line


*/

package org.checkstyle.suppressionxpathfilter.javadoc.javadoccontentlocation;

public interface InputXpathJavadocContentLocationTwo {

    // violation below 'Javadoc content should start from the same line as /*'
    /**
     * Text.
     */
    void test();
}
