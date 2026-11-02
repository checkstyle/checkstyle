/*
JavadocRegexp
format = alpha
ignoreCase = (default)false
ignoreMarkup = true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpCacheSecondFile {

    /**
     * Handles beta requests without a violation.
     */
    void betaRequest() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Handles alpha requests in another file.
     */
    void alphaRequest() {
    }
}
