/*
JavadocRegexp
format = (^|\\W)(aka|input|e\\.g\\.|viz\\.)(\\W|$)
ignoreCase = true
ignoreMarkup = true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpIgnoreMarkupTrue {

    /**
     * See <a href="https://example.com/aka">documentation</a>.
     */
    void validLinkHref() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Creates a user, aka an account owner.
     */
    void invalidText() {
    }

    // violation below 'Javadoc content matches'
    /**
     * The forbidden in<b>put</b> is split by markup.
     */
    void invalidSplitByHtml() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Use {@code E.g.} inside code.
     */
    void invalidCodeText() {
    }

    /**
     * <!-- aka hidden comment -->
     * Visible text is clean.
     */
    void validHtmlComment() {
    }
}
