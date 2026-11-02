/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethodsPackagePrivateAndClasses {

    public class A {
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

        /**
         * @param indent indentation to check.
         * @return true if {@code indent} less than minimal of
         *         acceptable indentation levels, false otherwise.
         */
        public boolean isGreaterThan(int indent) {
            return indent == 2;
        }

        /**
         * Sets whether to process JavaDoc or not.
         *
         * @param value Flag for processing JavaDoc.
         */
        public void setProcessJavadoc(boolean value) {
            value = true;
        }
    }

    public final class B {
        public int foo1(int a, int b) {return a + b;}

        protected int foo2(int a, int b) {return a + b;}

        public final int foo3(int a, int b) {return a + b;}

        protected final int foo4(int a, int b) {return a + b;}
    }

    public abstract class C {
        public abstract void foo1(int a);
    }
}

