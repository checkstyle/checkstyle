/*
OverloadMethodsDeclarationOrder
modifierGroups = static
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroups {

    void foo() {
    }

    void foo(String s) {
    }

    void bar() {
    }

    static void foo(int i) {
    }

    static void foo(long l) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    void foo(double d) {
    }

    static class InnerDefaultAndNoModifiers {

        public void foo() {
        }

        void bar() {
        }

        void foo(int i) {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo(String s) {
        }

    }

    interface InterfaceMethods {

        void foo();

        void foo(String s);

        static void foo(int i) {
        }

        void bar();

    }

    static class AnnotatedMethods {

        public void foo() {
        }

        @Deprecated
        void foo(int i) {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo(String s) {
        }

    }

    static class ConstructorBetween {

        public void foo() {
        }

        ConstructorBetween() {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo(int i) {
        }

    }

}
