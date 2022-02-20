/*
RedundantModifier


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

//non-compiled with javac: Compilable with Java16
public class InputRedundantModifierRecords {

    static record testRecord(int a) { // violation
    }

    interface foo {
        final static record testRecords(int a) { // 2 violations
        }
    }

    class b {
        static record testRecord(int a) { // violation
        }
    }

    enum testEnum {
        ONE;
        final static record testRecord() { // 2 violations

        }

        class b {
            static record testRecord() { // violation
            }
        }
    }
}

final record testRecord(int a) { // violation

    final record anotherRecord(int b) { // violation
    }

    static record anotherTestRecord(int c) { // violation
    }

    @interface hoo {
        final static record someRecord() { // 2 violations
        }
    }
}