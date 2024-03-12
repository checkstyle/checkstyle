/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationCheck1 {

    /****
     * Some Javadoc.
     *@see Something with violation
     ****/
    // violation above 'Line continuation .* expected level should be 4'
    void foo1() {}
}
