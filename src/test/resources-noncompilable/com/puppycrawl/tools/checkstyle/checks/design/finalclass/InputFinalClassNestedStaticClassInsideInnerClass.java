/*
FinalClass


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassNestedStaticClassInsideInnerClass {
    class M {
        class A {
            static class B {
                static class C { // violation 'Class C should be declared as final'
                    private C() {
                    }
                }
            }
        }
    }

    class Mw {
        static class B {
            static class C {
                private C() {
                }
            }
        }
    }

    class A {
        class B { // violation 'Class B should be declared as final'
            class C {
                private C() {}
            }
            class D extends C {
            }
            private B() {}
        }
    }

    class B {
        class C { // violation 'Class C should be declared as final'
            private C() {}
            class D extends Mw.B.C {
            }
        }
    }

    class P extends Q {
    }

    class Q {
        private Q() {
        }
    }

    class PR {
        static class P {
            static class Q { // violation 'Class Q should be declared as final'
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
        class F { // violation 'Class F should be declared as final'
            private F() {
            }
        }
    }

    class a {
        static class c { // violation 'Class c should be declared as final'
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
