/*
SuppressWithPlainTextCommentFilter
offCommentFormat = (default)// CHECKSTYLE:OFF
onCommentFormat = (default)// CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = foo


com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF
id = foo


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterSuppressByIdJavadocCheck {
    // CHECKSTYLE:OFF

    /** missing return **/
    int method3() { // filtered violation '@return tag should be present and have description.'
        return 3;
    }

    /** @param unused asd **/ // filtered violation 'Unused @param tag for 'unused'.'
    void method2() {
    }

    /**
     * Missing param tag.
     */
    void method3(int a) { // filtered violation 'Expected @param tag for 'a'.'
    }
    // CHECKSTYLE:ON

}
