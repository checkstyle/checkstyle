/*
OverloadMethodsDeclarationOrder


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderRecords {
    record MyRecord1() {
        public void foo(int i) {
        }

        public void foo(String s) {
        }

        public void notFoo() {
        }

        public void foo(int i, String s) { // violation
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

        public void foo() { // violation
        }

        public MyRecord2{}


    }

    class MyClass {
        public void foo(int i) {
        }

        public void notFoo() {
        }


        public void foo() { // violation
        }

        public MyClass() {
        }

        public void foo(int i, String s) {
        }

        public void foo(String s, int i) {
        }
    }
}
