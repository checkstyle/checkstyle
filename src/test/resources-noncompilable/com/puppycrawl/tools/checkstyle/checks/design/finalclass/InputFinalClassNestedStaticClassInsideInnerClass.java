/*
FinalClass


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassNestedStaticClassInsideInnerClass {
    class M {
        class A {
            static class B {
                static class C { // violation
                    private C() {
                    }
                }
            }
        }
    }

    class Mw {
        static class B {
            static class C { // ok
                private C() {
                }
            }
        }
    }

    class A {
        class B { // violation
            class C { // ok
                private C() {}
            }
            class D extends C {
            }
            private B() {}
        }
    }

    class B {
        class C { // violation
            private C() {}
            class D extends Mw.B.C {
            }
        }
    }

    class P extends Q {
    }

    class Q { // ok
        private Q() {
        }
    }

    class PR {
        static class P {
            static class Q { // violation
                private Q() {
                }
            }
        }
    }

    class F {
        private F() {
        }
    }

    class K extends F {
    }

    class X {
        class F { // violation
            private F() {
            }
        }
    }

    class a {
        static class c { // violation
            private c() {
            }
        }
    }

    class d {
        class e extends c {
        }
    }

    class c {
        private c() {
        }
    }
}
