/*
FinalClass


*/
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public enum InputFinalClassNestedInEnumWithAnonInnerClass {
    A;
    class n {
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
        class j {
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
