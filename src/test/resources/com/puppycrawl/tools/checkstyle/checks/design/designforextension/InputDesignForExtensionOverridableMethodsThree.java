/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethodsThree {

    public class A {
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

        @Deprecated
        /* */
        public void foo35() { }

        @Deprecated
        /** */
        public void foo36() { }

        @Deprecated
        /** */
        public void foo37() { /** */ }

        @Deprecated
        // comment
        public void foo38() { }

        // violation below ''foo39' does not have javadoc that explains how to do that safely'
        @Deprecated /** */
        public void foo39() {return; }

        void foo40() { // ok, empty body
            /** */
        }

        // violation below ''foo41' does not have javadoc that explains how to do that safely'
        void foo41() {
            return;
        }

        /** */
        void foo42() { // ok, has javadoc comment
        }

        /** */
        void foo43() {
            return;
        }

        /** */
        /* not empty */
        void foo44() {
            return;
        }

        /* not empty */
        /** */
        void foo45() {
            return;
        }
    }
}
