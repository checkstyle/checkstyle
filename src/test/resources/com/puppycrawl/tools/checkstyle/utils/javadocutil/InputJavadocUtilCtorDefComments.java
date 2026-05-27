package com.puppycrawl.tools.checkstyle.utils.javadocutil;

class InputJavadocUtilCtorDefComments {

    static class ModifierPath {
        /**modifier*/
        ModifierPath() {
        }
    }

    static class PublicModifierPath {
        /**publicModifier*/
        public PublicModifierPath() {
        }
    }

    static class TypeParameterPath {
        /**typeParams*/
        <T> TypeParameterPath(T value) {
        }
    }

    static class BodyCommentOnly {
        BodyCommentOnly() {
            /**nope*/
        }
    }

    static class DanglingReal {
        /**dangling*/
        /**real*/
        DanglingReal() {
        }
    }
}
