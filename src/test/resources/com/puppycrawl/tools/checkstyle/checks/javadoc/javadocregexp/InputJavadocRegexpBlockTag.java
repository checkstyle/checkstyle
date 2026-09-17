/*
JavadocRegexp
format = legacy|external
ignoreCase = true
ignoreMarkup = true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpBlockTag {

    // violation below 'Javadoc content matches'
    /**
     * Creates a value.
     *
     * @deprecated use legacy factory
     */
    void invalidDeprecatedTag() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Creates a value.
     *
     * @throws IllegalStateException when external state is used
     */
    void invalidThrowsTag() {
    }

    /**
     * Creates a value.
     *
     * @deprecated use replacement factory
     */
    void validBlockTag() {
    }
}
