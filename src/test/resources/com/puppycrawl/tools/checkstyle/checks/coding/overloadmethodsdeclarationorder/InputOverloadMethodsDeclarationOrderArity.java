/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = true

*/


package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderArity {

    public void testing() {
    }

    private void testing(int a, String b) {
    }

    public void testing(int a, int b) {
    }

    public static void testing(String a) {
    } // violation above 'Overloaded methods should be ordered by increasing parameter count.'

    public void testing(String a, String b) {
    } // violation above 'Overloaded methods should be ordered by increasing parameter count.'

    private class Inner {
        void test() {}

        void test(String str, Boolean b) {}

        void test(String str) {}
        // violation above 'Overloaded methods should be ordered by increasing parameter count.'

        void test(int x) {}
        // violation above 'Overloaded methods should be ordered by increasing parameter count.'
    }
    enum Foo {
        ONE, TWO;

        public void value() {
            value("");
        }

        public void value(String str, int a) {
        }

        public void value(String s) {
        } // violation above 'Overloaded methods should be ordered by increasing parameter count.'
    }
}
