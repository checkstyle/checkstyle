/*
JavadocRegexp
format = ^first second
ignoreCase = (default)false
ignoreMarkup = true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpTextSplitAcrossLines {

    // violation below 'Javadoc content matches'
    /**first
second*/
    void invalidTextSplitAcrossLines() {
    }
}
