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

class InputJavadocUtilCtorDefComments {

    static class ModifierPath {
        /**modifier*/
        ModifierPath(int value) { // violation 'Expected @param tag for 'value''
        }
    }

    static class PublicModifierPath {
        /**publicModifier*/
        public PublicModifierPath(int value) { // violation 'Expected @param tag for 'value''
        }
    }

    static class TypeParameterPath {
        // 2 violations 4 lines below:
        //      'Expected @param tag for '<T>''
        //      'Expected @param tag for 'value''
        /**typeParams*/
        <T> TypeParameterPath(T value) {
        }
    }

    static class BodyCommentOnly {
        BodyCommentOnly(int value) {
            /**local class javadoc*/
            class Local {
            }
        }
    }

    static class DanglingReal {
        /**dangling*/
        /**real*/
        DanglingReal(int value) { // violation 'Expected @param tag for 'value''
        }
    }
}
