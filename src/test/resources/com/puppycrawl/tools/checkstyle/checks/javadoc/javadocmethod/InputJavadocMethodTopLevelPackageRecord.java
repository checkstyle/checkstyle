/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = package
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

record InputJavadocMethodTopLevelPackageRecord(int a) {
    /**
     * Doc.
     */
    InputJavadocMethodTopLevelPackageRecord { // violation 'Expected @param tag for 'a'.'
    }

    /**
     * Doc.
     */
    void method(int x) { // violation 'Expected @param tag for 'x'.'
    }

    /**
     * Doc.
     */
    public void publicMethod(int x) {
    }
}
