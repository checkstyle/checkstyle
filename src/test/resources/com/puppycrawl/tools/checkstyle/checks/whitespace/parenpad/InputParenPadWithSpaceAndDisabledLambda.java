/*
ParenPad
option = SPACE
tokens = EXPR, METHOD_CALL, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadWithSpaceAndDisabledLambda {
    {
        java.util.function.Consumer a = ( o ) -> { o.toString( ); };

        java.util.function.Consumer b = o -> { o.toString(); };

        java.util.function.Consumer c = (o) -> { o.toString(); };

        java.util.function.Consumer d = (o ) -> { o.toString(); };

        java.util.function.Consumer e = ( o) -> { o.toString(); };

        java.util.stream.Stream.of().forEach( (o) -> o.toString() );

        java.util.stream.Stream.of().forEach( (Object o) -> o.toString() );

        java.util.stream.Stream.of().forEach( o -> o.toString() );
    }

    void someMethod(String param) { // 2 violations
    }
}
