/*
OverloadMethodsDeclarationOrder


*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderRecords {
    record MyRecord1() {
        public void foo(int i) {
        }

        public void foo(String s) {
        }

        public void notFoo() {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo(int i, String s) {
        }

        public void foo(String s, int i) {
        }
    }

    record MyRecord2() {
        public void foo(int i, String s) {
        }

        public void foo(String s, int i) {
        }

        public void foo(int i) {
        }
         public void notFoo(){

         }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo() {
        }

        public MyRecord2{}


    }

    class MyClass {
        public void foo(int i) {
        }

        public void notFoo() {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo() {
        }

        public MyClass() {
        }

        // violation below 'All overloaded methods should be placed next to each other.'
        public void foo(int i, String s) {
        }

        public void foo(String s, int i) {
        }
    }
}
