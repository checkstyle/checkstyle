/*
FinalClass


*/
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public interface InputFinalClassNestedInInterfaceWithAnonInnerClass {
    class a { // ok
        private a() {
        }
    }

    a obj = new a() {
    };

    interface foo {
        class m { // ok
            private m() {
            }
        }
    }

    class b { // violation
        private b() {
        }
        class foo {
            class m { // violation
                private m() {
                }
            }
        }
    }

    @interface annotatedInterface {
        class b { // ok
            private b() {
            }
        }

        b obj = new b () {
        };

        class c { // ok
            private c() {
            }
        }
    }

    class c extends foo.m { // violation
        private c() {
        }
    }

    class h extends annotatedInterface.c {
    }
}
