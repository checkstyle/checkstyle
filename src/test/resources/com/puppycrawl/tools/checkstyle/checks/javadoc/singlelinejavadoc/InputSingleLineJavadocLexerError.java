/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = (default)
ignoreInlineTags = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.singlelinejavadoc;

class InputSingleLineJavadocLexerError {

    /** @@{@return} */
    // violation above 'Javadoc comment at column 4 has parse error.'
    void foo() {
    }

    /** @throws Exception if a problem occurs */
    // violation above 'Single-line Javadoc comment should be multi-line.'
    void foo2() {}
}
