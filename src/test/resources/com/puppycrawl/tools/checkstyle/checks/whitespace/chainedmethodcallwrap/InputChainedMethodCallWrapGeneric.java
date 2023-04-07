/*
ChainedMethodCallWrap
identifierPattern = ^obj\.get\..*$
maxCallsInMultiLine = (default)1
maxCallsInSingleLine = (default)1

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

public class InputChainedMethodCallWrapGeneric {

    public void method() {
        InputChainedMethodCallWrapGeneric obj = new InputChainedMethodCallWrapGeneric();
        // violation below '2 method calls on single line, max allowed is 1'
        obj.<Integer>get(1).getClass();

        // violation below '2 method calls on single line, max allowed is 1'
        ((InputChainedMethodCallWrapGeneric)obj.<InputChainedMethodCallWrapGeneric>get(obj)).get();

        ((InputChainedMethodCallWrapGeneric) obj.<InputChainedMethodCallWrapGeneric>get(obj))
            .<Integer>get().getClass();
        // violation above 'Wrap chained method call, max allowed on a line is 1'

        // violation below '2 method calls on single line, max allowed is 1'
        obj.<Integer>get(1).<Integer>get(4);
    }

    private <T> InputChainedMethodCallWrapGeneric get(T t) {
        return null;
    }

    private Object get() {
        return null;
    }

}
