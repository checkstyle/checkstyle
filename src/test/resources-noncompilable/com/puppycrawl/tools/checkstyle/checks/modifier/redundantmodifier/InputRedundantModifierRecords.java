/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF,VARIABLE_DEF,ANNOTATION_FIELD_DEF,INTERFACE_DEF,CTOR_DEF,CLASS_DEF, \
          ENUM_DEF,RESOURCE,ANNOTATION_DEF,RECORD_DEF,PATTERN_VARIABLE_DEF,LITERAL_CATCH,LAMBDA

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

//non-compiled with javac: Compilable with Java17
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

record anotherRecord(int b) {

    record nestedRecord(int a, int p) {
    }
}
