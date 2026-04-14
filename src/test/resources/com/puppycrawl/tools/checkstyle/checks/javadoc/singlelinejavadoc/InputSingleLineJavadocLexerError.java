/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = (default)
ignoreInlineTags = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.singlelinejavadoc;

class InputSingleLineJavadocLexerError {

    /** @@{@return} */
    void foo() {
        // violation 2 lines above 'Javadoc comment at column 4 has parse error.'
    }

    /** @throws Exception if a problem occurs */
    void foo2() {
         // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
    }
}
