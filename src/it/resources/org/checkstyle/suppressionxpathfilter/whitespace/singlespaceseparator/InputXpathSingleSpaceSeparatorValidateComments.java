/*
SingleSpaceSeparator
validateComments = true

*/

package org.checkstyle.suppressionxpathfilter.whitespace.singlespaceseparator;

public class InputXpathSingleSpaceSeparatorValidateComments {
    // violation below 'Use a single space to separate non-whitespace characters.'
    long y;     // an invalid comment // warn
}
