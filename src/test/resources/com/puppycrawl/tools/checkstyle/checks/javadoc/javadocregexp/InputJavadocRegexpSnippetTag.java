/*
JavadocRegexp
format = lang="e\\.g\\."|forbidden\\s*=
ignoreCase = (default)false
ignoreMarkup = (default)false
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpSnippetTag {

    // violation below 'Javadoc content matches'
    /**
     * {@snippet lang="e.g." :
     *   int value = 10;
     * }
     */
    void invalidSnippetAttribute() {
    }

    // violation below 'Javadoc content matches'
    /**
     * {@snippet :
     *   int forbidden = 10;
     * }
     */
    void invalidSnippetBody() {
    }

    /**
     * {@snippet :
     *   int value = 20;
     * }
     */
    void validSnippetTag() {
    }
}
