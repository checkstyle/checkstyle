/*
HiddenField
ignoreConstructorParameter = true
ignoreSetter = true
setterCanReturnItsClass = true
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.time.Clock;
import java.time.Instant;

public class InputHiddenFieldRecords2 {
}

class Scratch {

    private final Clock clock;

    Scratch(Clock clock) {
        this.clock = clock;
    }

    public record State( String token, Instant expiresAt) {
        public boolean isFresh(final Clock clock) {
            return Instant.now(clock).isBefore(expiresAt);
        }
    }
}
