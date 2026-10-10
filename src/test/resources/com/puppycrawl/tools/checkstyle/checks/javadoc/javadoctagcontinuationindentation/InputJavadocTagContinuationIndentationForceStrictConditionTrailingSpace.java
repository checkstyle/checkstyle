/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4
forceStrictCondition = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationForceStrictConditionTrailingSpace {

    /**
     * Line with only trailing spaces is not a violation.
     *
     * @param value first line of description
     *          
     *     continuation after line with only spaces.
     */
    public void lineWithOnlySpaces(int value) {
    }
}
