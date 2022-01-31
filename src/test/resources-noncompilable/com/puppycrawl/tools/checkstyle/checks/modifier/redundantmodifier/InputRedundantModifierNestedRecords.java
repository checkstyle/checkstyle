/*
RedundantModifier


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

//non-compiled with javac: Compilable with Java16
public class InputRedundantModifierNestedRecords {

    static record testRecord(int a) { // violation
    }

    interface foo {
        static record testRecords(int a) { // violation
        }
    }

    class b {
        static record testRecord(int a) { // violation
        }
    }

    enum testEnum {
        ONE;
        static record testRecord() { // violation

        }

        class b {
            static record testRecord() { // violation
            }
        }
    }
}

record testRecord(int a) {

    record anotherRecord(int b) { // ok
    }

    static record anotherTestRecord(int c) { // violation
    }

    @interface hoo {
        static record someRecord() { // violation
        }
    }
}
