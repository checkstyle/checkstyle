/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodBlockComment {

    /**
     * @return value
     */
    public int /* regular block comment */ method1() {
        return 1;
    }

    // violation 4 lines below '@return tag should be present and have description.'
    /**
     * Javadoc.
     */
    public int /* regular block comment */ method2() {
        return 1;
    }

    public /* regular block comment */ int method3() {
        return 1;
    }

}
