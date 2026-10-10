/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public record InputJavadocMethodTopLevelRecord(int a) {
    /**
     * Doc.
     */
    public InputJavadocMethodTopLevelRecord { // violation 'Expected @param tag for 'a'.'
    }

    /**
     * Doc.
     */
    public void method(int x) { // violation 'Expected @param tag for 'x'.'
    }
}
