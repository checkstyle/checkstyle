package org.checkstyle.checks.annotation.suppresswarnings;

public class InputXpathSuppressWarningsVariableDefinition {
        @SuppressWarnings("") // warn
        private final String foo;

    public InputXpathSuppressWarningsVariableDefinition(String foo) {
        this.foo = foo;
    }
}
