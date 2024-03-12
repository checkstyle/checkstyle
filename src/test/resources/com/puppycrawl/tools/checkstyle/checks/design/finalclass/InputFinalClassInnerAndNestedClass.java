/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassInnerAndNestedClass {

    private class SuperClass {
        private SuperClass() {
        }
    }

    private class SubClass extends SuperClass {
    } // violation above 'Class SubClass should be declared as final'

    class SameName { // violation
        private SameName() {
        }
    }

    static class Test {
        static class SameName {
            private SameName() {
            }
            class Test3 {
            }
        }
    }

    class TestInnerClass {
        class SameName {
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
        class B {
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

class foo2 {
    class c {
        private c() {
        }
    }

    class a extends c {
        class c { // violation
            private c() {
            }
        }
    }
}
