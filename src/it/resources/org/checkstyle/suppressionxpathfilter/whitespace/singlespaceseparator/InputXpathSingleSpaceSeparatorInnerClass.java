/*
SingleSpaceSeparator
validateComments = (default)false

*/

package org.checkstyle.suppressionxpathfilter.whitespace.singlespaceseparator;

public class InputXpathSingleSpaceSeparatorInnerClass {
    class Inner {
        // violation below 'Use a single space to separate non-whitespace characters.'
        long  bad;//warn
    }
}
