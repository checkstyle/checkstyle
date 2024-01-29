/*
FinalClass


*/
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public interface InputFinalClassNestedInInterfaceWithAnonInnerClass {
    class a {
        private a() {
        }
    }

    a obj = new a() {
    };

    interface foo {
        class m {
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
        class b {
            private b() {
            }
        }

        b obj = new b () {
        };

        class c {
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
