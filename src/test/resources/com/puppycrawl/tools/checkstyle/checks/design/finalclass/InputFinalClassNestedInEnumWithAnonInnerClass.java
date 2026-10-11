/*
FinalClass


*/
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public enum InputFinalClassNestedInEnumWithAnonInnerClass {
    A;
    class n {
        private n() {
        }
        class j { // violation 'Class j should be declared as final'
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
        class n { // violation 'Class n should be declared as final'
            private n() {
            }
        }
    }

    n obj = new n() {
    };
}
