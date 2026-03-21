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
        // violation 2 lines above
    }

    /** @throws Exception if a problem occurs */ // violation
    void foo2() {}
}
