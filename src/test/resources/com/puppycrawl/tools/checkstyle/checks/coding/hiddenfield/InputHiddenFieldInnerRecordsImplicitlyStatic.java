/*
HiddenField
ignoreConstructorParameter = true
ignoreSetter = true
setterCanReturnItsClass = true
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT
ignoreFormat = (default)(null)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.time.Clock;
import java.time.Instant;

public class InputHiddenFieldInnerRecordsImplicitlyStatic {
}

class Scratch {

    private final Clock clock;

    Scratch(Clock clock) {
        this.clock = clock;
    }

    public record State(String token, Instant expiresAt) {
        static int pointer = 0;
        // Inner records are implicitly static so the outer class field will not hide this paramter
        public boolean isFresh(final Clock clock) {
            return Instant.now(clock).isBefore(expiresAt);
        }

        public int anInt(int pointer) { // violation ''pointer' hides a field'
            return pointer;
        }
    }
}

class TestOne {

    String name;

    record data(String str, int integer) {
        void method() {
            String name = str;
        }

        public boolean isTrue(String name) {
            return true;
        }
    }
}
