/*
RedundantModifier


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

//non-compiled with javac: Compilable with Java16
public class InputRedundantModifierRecords {

    static record testRecord(int a) { // violation 'Redundant 'static' modifier'
    }

    interface foo {
        final static record testRecords(int a) { // 2 violations
        }
    }

    class b {
        static record testRecord(int a) { // violation 'Redundant 'static' modifier'
        }
    }

    enum testEnum {
        ONE;
        final static record testRecord() { // 2 violations

        }

        class b {
            static record testRecord() { // violation 'Redundant 'static' modifier'
            }
        }
    }
}

final record testRecord(int a) { // violation 'Redundant 'final' modifier'

    final record anotherRecord(int b) { // violation 'Redundant 'final' modifier'
    }

    static record anotherTestRecord(int c) { // violation 'Redundant 'static' modifier'
    }

    @interface hoo {
        final static record someRecord() { // 2 violations
        }
    }
}

record anotherRecord(int b) { // ok

    record nestedRecord(int a, int p) { // ok
    }
}
