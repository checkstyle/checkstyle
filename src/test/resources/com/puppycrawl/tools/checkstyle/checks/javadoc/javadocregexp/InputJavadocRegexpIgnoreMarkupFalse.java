/*
JavadocRegexp
format = <br\\s*[/]?>|aka
ignoreCase = (default)false
ignoreMarkup = (default)false
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpIgnoreMarkupFalse {

    // violation below 'Javadoc content matches'
    /**
     * Uses an HTML line break. <br>
     */
    void invalidHtmlTag() {
    }

    // violation below 'Javadoc content matches'
    /**
     * See <a href="https://example.com/aka">documentation</a>.
     */
    void invalidHrefInRawSource() {
    }

    /**
     * Uses regular visible text.
     */
    void validText() {
    }
}
