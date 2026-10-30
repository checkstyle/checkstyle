/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethodsComments {

    public class A {
        // violation below ''foo1' does not have javadoc that explains how to do that safely'
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

        // violation below ''foo8' does not have javadoc that explains how to do that safely'
        public int foo8(int a, int b) {
            // single-line comment before content
            return a + b;
        }

        // violation below ''foo9' does not have javadoc that explains how to do that safely'
        public int foo9(int a, int b) {
            /*
             * block comment before content
             */
            return a + b;
        }

        // violation below ''foo10' does not have javadoc that explains how to do that safely'
        public int foo10(int a, int b) {
            /**
             * javadoc block comment before content
             */
            return a + b;
        }

        // violation below ''foo11' does not have javadoc that explains how to do that safely'
        public int foo11(int a, int b) {
            return a + b;
            // single-line comment after content
        }

        // violation below ''foo12' does not have javadoc that explains how to do that safely'
        public int foo12(int a, int b) {
            return a + b;
            /*
             * block comment after content
             */
        }

        // violation below ''foo13' does not have javadoc that explains how to do that safely'
        public int foo13(int a, int b) {
            return a + b;
            /**
             * javadoc block comment after content
             */
        }

        // violation below ''foo14' does not have javadoc that explains how to do that safely'
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
    }
}

