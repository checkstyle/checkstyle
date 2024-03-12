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
            class b {
                private b() {
                }
            }
        }
        class m { // violation
            private m() {
            }
            class q {
                private q() {
                }
            }
        }
    }

    class m {
        private m() {
        }
        class q { // violation
            private q() {
            }
        }
        j.m.q obj = new j().new m().new q() {
        };
    }

    class jasper {
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

    class g {
        private g() {
        }
    }

    class n {
        class g { // violation
            private g() {
            }
        }
        class y { // violation
            private y() {
            }
        }
        private n() {
        }
    }

    class va {
        n obj = new n() {
        };
    }
    class vl {
        class n { // violation
            private n() {
            }
        }
    }

    class var1 {
        class n { // violation
            private n() {
            }
        }
    }

    class gang {
        g obj = new g() {
        };
    }

    jasper obj1 = new com.puppycrawl.tools.checkstyle.checks.design.finalclass
        .InputFinalClassAnonymousInnerClass().new jasper() {
    };

    test3 obj2 = new test3() { // test3 is in another file
    };

    class x {
        class y {
            private y() {
            }
            class z {
                y obj = new y() {
                };
            }
        }
    }
}
