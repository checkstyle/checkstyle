package org.checkstyle.suppressionxpathfilter.annotation.suppresswarnings;

public class InputXpathSuppressWarningsVariableDefinition {
        @SuppressWarnings("") // warn
        private final String foo;

    public InputXpathSuppressWarningsVariableDefinition(String foo) {
        this.foo = foo;
    }
}
