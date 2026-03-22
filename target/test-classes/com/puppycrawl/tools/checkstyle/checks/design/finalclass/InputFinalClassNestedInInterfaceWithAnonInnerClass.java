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

    class b { // violation 'Class b should be declared as final'
        private b() {
        }
        class foo {
            class m { // violation 'Class m should be declared as final'
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

    class c extends foo.m { // violation 'Class c should be declared as final'
        private c() {
        }
    }

    class h extends annotatedInterface.c {
    }
}
