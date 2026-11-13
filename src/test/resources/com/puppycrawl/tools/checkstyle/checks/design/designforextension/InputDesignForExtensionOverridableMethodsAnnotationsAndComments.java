/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethodsAnnotationsAndComments {

    public class A {
        // Single line comment
        // violation below ''foo22' does not have javadoc that explains how to do that safely'
        public void foo22() {
            return;
        }

        // Single line comments
        // organized in a block
        // violation below ''foo23' does not have javadoc that explains how to do that safely'
        public void foo23() {
            return;
        }

        // Single line comments
        // organized in a block
        public void foo24() {}

        /* Block comment violation */
        // violation below ''foo25' does not have javadoc that explains how to do that safely'
        public void foo25() {
            return;
        }

        // Single line comment
        // violation below ''foo26' does not have javadoc that explains how to do that safely'
        @Deprecated
        public void foo26() {
            return;
        }

        // Single line comments
        // organized in a block
        // violation below ''foo27' does not have javadoc that explains how to do that safely'
        @Deprecated
        public void foo27() {
            return;
        }

        /** Javadoc comment */
        @Deprecated
        public void foo28() {
            return;
        }

        /* Block comment violation */
        // violation below ''foo29' does not have javadoc that explains how to do that safely'
        @Deprecated
        public void foo29() {
            return;
        }

        /**
         * Returns maximum of a and b.
         * @param a a.
         * @param b b.
         * @return maximum of a and b.
         */
        public int max(int a, int b) {
            return Math.max(a, b);
        }

        /** */
        public int foo30() {
            /** */
            return 1;
        }

        /* */
        // violation below ''foo31' does not have javadoc that explains how to do that safely'
        public int foo31() {
            /** */
            return 1;
        }

        /** */
        public int foo32() {
            /* */
            return 1;
        }

        // violation below ''foo33' does not have javadoc that explains how to do that safely'
        @Deprecated
        /** */
        public int foo33() {
            return 1;
        }

        // violation below ''foo34' does not have javadoc that explains how to do that safely'
        @Deprecated
        /* */
        public int foo34() {
            return 1;
        }
    }
}

