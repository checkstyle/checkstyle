/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = true
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF
id = javadoc-method


com.puppycrawl.tools.checkstyle.filters.SuppressWithPlainTextCommentFilter
offCommentFormat = (default)// CHECKSTYLE:OFF
onCommentFormat = (default)// CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = javadoc-method

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodModuleId {
    // CHECKSTYLE:OFF

    /** Missing return tag. */
    public int missingReturn() { // filtered violation '@return tag should be present'
        return 1;
    }
    // filtered violation below 'Unused @param tag for 'unused'.'
    /** @param unused description */
    public void unusedParam() {
    }

    // filtered violation 2 lines below 'Unclosed HTML tag found: p'
    /**
     * <p>
     */
    public void unclosedHtml() {
    }

    // CHECKSTYLE:ON

    /** Missing return tag outside the suppression region. */
    public int notSuppressed() { // violation '@return tag should be present'
        return 2;
    }
}
