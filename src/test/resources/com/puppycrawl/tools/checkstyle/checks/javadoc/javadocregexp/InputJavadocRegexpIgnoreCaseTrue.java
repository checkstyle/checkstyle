/*
JavadocRegexp
format = AKA
ignoreCase = true
ignoreMarkup = true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpIgnoreCaseTrue {

    // violation below 'Javadoc content matches'
    /**
     * Lowercase aka is matched.
     */
    void invalid() {
    }
}
