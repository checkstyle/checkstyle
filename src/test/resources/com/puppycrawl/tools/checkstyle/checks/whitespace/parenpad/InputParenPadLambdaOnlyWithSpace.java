/*
ParenPad
option = SPACE
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadLambdaOnlyWithSpace {
    {
        java.util.function.Consumer a = ( o ) -> { o.toString( ); };

        java.util.function.Consumer b = o -> { o.toString(); };

        java.util.function.Consumer c = (o) -> { o.toString(); }; // 2 violations

        java.util.function.Consumer d = (o ) -> { o.toString(); }; // violation

        java.util.function.Consumer e = ( o) -> { o.toString(); }; // violation

        java.util.stream.Stream.of().forEach( (o) -> o.toString() ); // 2 violations

        java.util.stream.Stream.of().forEach( (Object o) -> o.toString() ); // 2 violations

        java.util.stream.Stream.of().forEach( o -> o.toString() );
    }

    void someMethod(String param) {
    }
}
