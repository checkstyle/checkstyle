/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassAnonymousInnerClass {
    static class a {
        static class b { // violation
            private b() {
            }
        }
    }

    static class j {
        a.b object = new a().new b() {
        };

        class a {
            class b { // ok
                private b() {
                }
            }
        }
        class m { // violation
            private m() {
            }
            class q { // ok
                private q() {
                }
            }
        }
    }

    class m { // ok
        private m() {
        }
        class q { // violation
            private q() {
            }
        }
        j.m.q obj = new j().new m().new q() {
        };
    }

    class jasper { // ok
        private jasper() {
        }
        class a {
            class b { // violation
                private b() {
                }
            }
            m obj = new m() {
            };
        }
    }

    jasper obj = new com.puppycrawl.tools.checkstyle.checks.design.finalclass
        .InputFinalClassAnonymousInnerClass().new jasper() {
    };

    test3 obj2 = new test3() { // test3 is in another file
    };
}
