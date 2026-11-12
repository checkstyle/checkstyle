/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionOverridableMethodsFour {

    public class A {
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
