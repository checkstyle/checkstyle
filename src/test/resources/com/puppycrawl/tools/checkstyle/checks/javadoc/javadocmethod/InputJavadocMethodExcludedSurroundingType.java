/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodExcludedSurroundingType {
    private static class PrivateType {
        /** @return ignored because the surrounding type is private */
        public void process() {
        }
    }

    private final Runnable anonymous = new Runnable() {
        /** @return ignored because this is an anonymous class */
        public void run() {
        }
    };
}
