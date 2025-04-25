/*
CovariantEquals


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

public class InputCovariantEqualsRecords {

    public record MyRecord1() {
        public boolean equals(String str) { // violation 'covariant equals'
            return str.equals(this);
        }
    }

    public record MyRecord2() {
        public boolean equals(String str) {
            return str.equals(this);
        }
        public boolean equals(Object obj) {
            return false;
        }
    }

    public record MyRecord3() {
        record MyInnerRecord() {
            public boolean equals(String str) { // violation 'covariant equals'
                return str.equals(this);
            }
        }
    }
}
