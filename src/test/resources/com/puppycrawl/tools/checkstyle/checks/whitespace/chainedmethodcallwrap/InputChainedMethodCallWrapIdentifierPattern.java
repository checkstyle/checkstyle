/*
ChainedMethodCallWrap
identifierPattern = ^Pizza.Builder.*$
maxCallsPerLine = (default)1
maxSingleLineCalls = (default)1

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

public class InputChainedMethodCallWrapIdentifierPattern {
    static void method() {
        new Pizza.Builder().bacon()
                .cheese().pepperoni() // violation 'Chained method call should be wrapped'
                .build();

        new Pizza.Builder().cheese().build();
                           // violation above ''2' method calls on single line, max allowed is '1''
    }

    static void someMethod() {
        new Pizza.Builder().cheese() // ok, constructor calls are not counted
                .bacon()
                .cheese().pepperoni().build(); // 2 violations

        new Pizza.Builder().build(); // ok
        InputChainedMethodCallWrapIdentifierPattern.method();
        new Pizza.Builder().cheese().bacon() // violation 'Chained method call should be wrapped'
                .bacon()
                .build();
        if(!!(new Pizza.Builder().cheese().build()).isFresh()) {
                            // violation above ''3' method calls on single line, max allowed is '1''
        }
    }
}

class Pizza {
    public static class Builder {

        public Builder() {
        }

        public Builder cheese() {
            return this;
        }

        public Builder pepperoni() {
            return this;
        }

        public Builder bacon() {
            return this;
        }

        public Pizza build() {
            return new Pizza();
        }
    }

    public boolean isFresh() {
        return true;
    }
}

