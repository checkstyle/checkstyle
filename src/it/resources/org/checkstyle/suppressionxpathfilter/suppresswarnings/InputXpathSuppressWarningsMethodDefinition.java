package org.checkstyle.suppressionxpathfilter.suppresswarnings;

public class InputXpathSuppressWarningsMethodDefinition {
        private final String foo;

    public InputXpathSuppressWarningsMethodDefinition(String myFoo) {
        this.foo = myFoo;
    }

    @SuppressWarnings("") // warn
    public String getFoo() {
        return this.foo;
    }
}
