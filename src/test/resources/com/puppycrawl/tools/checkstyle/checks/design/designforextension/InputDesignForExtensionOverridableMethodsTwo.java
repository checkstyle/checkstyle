/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethodsTwo {

    public class A {
        public final int foo15(int a) {return a - 2;}

        protected final int foo16(int a) {return a - 2;}

        /** Javadoc comment */
        protected int foo17(int a) {return a -1;}

        /** Method javadoc */
        public void foo18() { }

        /** Method javadoc */
        public int foo19() {return 1;}

        /** */
        public final int foo20(int a) {return a - 2;}

        /** */
        protected final int foo21(int a) {return a - 2;}

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
    }
}
