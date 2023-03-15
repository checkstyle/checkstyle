/*
ParenPad
option = (default)nospace
tokens = EXPR, METHOD_CALL, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadWithDisabledLambda {
    {
        java.util.function.Consumer a = (o) -> { o.toString(); }; // ok

        java.util.function.Consumer b = o -> { o.toString(); }; // ok

        java.util.function.Consumer c = ( o ) -> { o.toString(); }; // ok

        java.util.function.Consumer d = (o ) -> { o.toString(); }; // ok

        java.util.function.Consumer e = ( o) -> { o.toString(); }; // ok

        java.util.stream.Stream.of().forEach(( o ) -> o.toString()); // ok

        java.util.stream.Stream.of().forEach(( Object o ) -> o.toString()); // ok

        java.util.stream.Stream.of().forEach(o -> o.toString( )); // 2 violations
    }

    void someMethod( String param ) { // 2 violations
    }
}
