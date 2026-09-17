/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = true

*/


package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderMisc {
    void test() {}

    void foo() {}

    // because overloads never split
    // violation below 'All overloaded methods should be placed next to each other.'
    void test(int a, int b) {}

    void test(String a) {}
    // violation above 'Overloaded methods should be ordered by increasing parameter count.'

    record MyRecord1() {
        public void foo(int i) {
        }

        public void foo(String s, int j) {
        }

        public void notFoo() {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo(String s) {
        } // violation above 'Overloaded methods should be ordered by increasing parameter count.'

        public void foo(boolean b) {
        } // violation above 'Overloaded methods should be ordered by increasing parameter count.'
    }

    class AllGood {
        void test() {}

        void test(int a) {}

        void test(int a, int b) {}

        void test(int a, int b, int c) {}
    }

}
