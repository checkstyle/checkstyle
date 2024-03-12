/*
FinalClass


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public record InputFinalClassNestedInRecord(int a) {

    record b() {
        class c { // violation
            private c() {
            }
        }
    }

    class k {
        c obj = new c() {
        };

        class c {
            private c() {
            }
        }
    }

    record s() {
        record f() {
            class j { // violation
                private j() {
                }
            }
        }
    }

    static class h {
        record f() {
            final static f.j obj = new f.j() {
            };

            static class j {
                private j() {
                }
            }
        }
    }
    private class Nothing { // violation 'Class Nothing should be declared as final'
    }
}
