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

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

public class InputJavadocUtilCompactCtorDefComments {

    public record ModifierPath(String value) {

        /**modifier*/
        public ModifierPath { // violation "Expected @param tag for 'value'"
        }

    }

    public record BodyCommentOnly(String value) {

        public BodyCommentOnly {
            /**local class javadoc*/
            class Local {
            }
        }

    }

    public record DanglingReal(String value) {

        /**dangling*/
        /**real*/
        public DanglingReal { // violation "Expected @param tag for 'value'"
        }

    }

}
