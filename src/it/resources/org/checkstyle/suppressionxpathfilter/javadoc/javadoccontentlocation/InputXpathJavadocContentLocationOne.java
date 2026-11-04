/*
JavadocContentLocation
location = (default)SECOND_LINE


*/

package org.checkstyle.suppressionxpathfilter.javadoc.javadoccontentlocation;

public interface InputXpathJavadocContentLocationOne {

    // violation below 'Javadoc content should start from the next line after /*'
    /** Text.
     */
    void test();
}
