/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethods {

    public class A {
        // violation below 'Method 'foo1' does not have javadoc that explains'
        public int foo1(int a, int b) {return a + b;}

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

        // violation below 'Method 'foo8' does not have javadoc that explains'
        public int foo8(int a, int b) {
            // single-line comment before content
            return a + b;
        }

        // violation below 'Method 'foo9' does not have javadoc that explains'
        public int foo9(int a, int b) {
            /*
             * block comment before content
             */
            return a + b;
        }

        // violation below 'Method 'foo10' does not have javadoc that explains'
        public int foo10(int a, int b) {
            /**
             * javadoc block comment before content
             */
            return a + b;
        }

        // violation below 'Method 'foo11' does not have javadoc that explains'
        public int foo11(int a, int b) {
            return a + b;
            // single-line comment after content
        }

        // violation below 'Method 'foo12' does not have javadoc that explains'
        public int foo12(int a, int b) {
            return a + b;
            /*
             * block comment after content
             */
        }

        // violation below 'Method 'foo13' does not have javadoc that explains'
        public int foo13(int a, int b) {
            return a + b;
            /**
             * javadoc block comment after content
             */
        }

        // violation below 'Method 'foo14' does not have javadoc that explains'
        protected int foo14(int a) {return a -1;}

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
        // violation below 'Method 'foo22' does not have javadoc that explains'
        public void foo22() {
            return;
        }

        // Single line comments
        // organized in a block
        // violation below 'Method 'foo23' does not have javadoc that explains'
        public void foo23() {
            return;
        }

        // Single line comments
        // organized in a block
        public void foo24() {}

        /* Block comment violation */
        // violation below 'Method 'foo25' does not have javadoc that explains'
        public void foo25() {
            return;
        }

        // Single line comment
        // violation below 'Method 'foo26' does not have javadoc that explains'
        @Deprecated
        public void foo26() {
            return;
        }

        // Single line comments
        // organized in a block
        // violation below 'Method 'foo27' does not have javadoc that explains'
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
        // violation below 'Method 'foo29' does not have javadoc that explains'
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
        // violation below 'Method 'foo31' does not have javadoc that explains'
        public int foo31() {
            /** */
            return 1;
        }

        /** */
        public int foo32() {
            /* */
            return 1;
        }

        // violation below 'Method 'foo33' does not have javadoc that explains'
        @Deprecated
        /** */
        public int foo33() {
            return 1;
        }

        // violation below 'Method 'foo34' does not have javadoc that explains'
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

        // violation below 'Method 'foo39' does not have javadoc that explains'
        @Deprecated /** */
        public void foo39() {return; }

        void foo40() { // ok, empty body
            /** */
        }

        // violation below 'Method 'foo41' does not have javadoc that explains'
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
