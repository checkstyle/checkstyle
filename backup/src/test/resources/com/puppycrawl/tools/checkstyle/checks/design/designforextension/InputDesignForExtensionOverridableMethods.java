/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethods {

    public class A {
        public int foo1(int a, int b) {return a + b;} // violation

        public void foo2() { }

        public void foo3(int a, int b) { }

        private int foo4(int a, int b) {return a + b;}

        public void foo5() {
            // single-line comment is not counted as a content
        }

        public void foo6() {
            /**
             * javadoc block comment is not counted as a content
             */
        }

        public void foo7() {
            /*
             * block comment is not counted as a content
             */
        }

        public int foo8(int a, int b) { // violation
            // single-line comment before content
            return a + b;
        }

        public int foo9(int a, int b) { // violation
            /*
             * block comment before content
             */
            return a + b;
        }

        public int foo10(int a, int b) { // violation
            /**
             * javadoc block comment before content
             */
            return a + b;
        }

        public int foo11(int a, int b) { // violation
            return a + b;
            // single-line comment after content
        }

        public int foo12(int a, int b) { // violation
            return a + b;
            /*
             * block comment after content
             */
        }

        public int foo13(int a, int b) { // violation
            return a + b;
            /**
             * javadoc block comment after content
             */
        }

        protected int foo14(int a) {return a -1;} // violation

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
        public void foo22() { // violation
            return;
        }

        // Single line comments
        // organized in a block
        public void foo23() { // violation
            return;
        }

        // Single line comments
        // organized in a block
        public void foo24() {}

        /* Block comment violation */
        public void foo25() { // violation
            return;
        }

        // Single line comment
        @Deprecated // violation
        public void foo26() {
            return;
        }

        // Single line comments
        // organized in a block
        @Deprecated // violation
        public void foo27() {
            return;
        }

        /** Javadoc comment */
        @Deprecated
        public void foo28() {
            return;
        }

        /* Block comment violation */
        @Deprecated // violation
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
        public int foo31() { // violation
            /** */
            return 1;
        }

        /** */
        public int foo32() {
            /* */
            return 1;
        }

        @Deprecated // violation
        /** */
        public int foo33() {
            return 1;
        }

        @Deprecated // violation
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

        @Deprecated /** */ // violation
        public void foo39() {return; }

        void foo40() { // no violation: empty body
            /** */
        }

        void foo41() { // violation
            return;
        }

        /** */
        void foo42() { // no violation: has javadoc comment
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
