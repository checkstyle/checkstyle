/*
FinalClass


*/
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public enum InputFinalClassNestedInEnumWithAnonInnerClass {
    A;
    class n { // ok
        private n() {
        }
        class j { // violation
            private j() {
            }
        }
    }

    enum k {
        B;
        j obj = new j() {
        };
        class j { // ok
            private j() {
            }
        }
        class n { // violation
            private n() {
            }
        }
    }

    n obj = new n() {
    };
}
