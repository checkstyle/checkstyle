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

public class InputJavadocMethodSurroundingAccessModifierRecord {

    public record PublicRecord(int a) {
        /**
         * Doc.
         */
        public PublicRecord { // violation 'Expected @param tag for 'a'.'
        }

        /**
         * Doc.
         */
        public void method(int x) { // violation 'Expected @param tag for 'x'.'
        }
    }

    record PackageRecord(int a) {
        /**
         * Doc.
         */
        public void method(int x) {
        }
    }

    private record PrivateRecord(int a) {
        /**
         * Doc.
         */
        public PrivateRecord {
        }

        /**
         * Doc.
         */
        public void method(int x) {
        }

        public record PublicRecordInPrivateRecord(int b) {
            /**
             * Doc.
             */
            public void method(int x) { // violation 'Expected @param tag for 'x'.'
            }
        }
    }

    private static class PrivateClass {
        public record PublicRecordInPrivateClass(int a) {
            /**
             * Doc.
             */
            public void method(int x) { // violation 'Expected @param tag for 'x'.'
            }
        }

        private record PrivateRecordInPrivateClass(int a) {
            /**
             * Doc.
             */
            public void method(int x) {
            }
        }
    }
}
