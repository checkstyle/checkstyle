/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassInnerAndNestedClass {

    private class SuperClass { // ok
        private SuperClass() {
        }
    }

    private class SubClass extends SuperClass {
    }

    class SameName { // violation
        private SameName() {
        }
    }

    static class Test {
        static class SameName { // ok
            private SameName() {
            }
            class Test3 {
            }
        }
    }

    class TestInnerClass {
        class SameName { // ok
            class Test3 {
                class Test4 extends SameName {
                }
            }
            private SameName() {
            }
        }
    }

    class TestNestedClasses {
        class SameName { // violation
            private SameName() {
            }
            class Test3 {
                class Test4 extends Test.SameName {
                }
            }
        }
    }
}

enum foo {
    VALUE_1, VALUE_2;

    class A {
        class B { // ok
            private B() {
            }
        }

        class c extends B {
        }

        class D {
            class B { // violation
                private B() {
                }
            }
        }
    }
}

