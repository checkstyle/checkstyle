package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadLambda {
    {
        java.util.function.Consumer a = (o) -> { o.toString(); }; // ok

        java.util.function.Consumer b = o -> { o.toString(); }; // ok

        java.util.function.Consumer c = ( o ) -> { o.toString(); }; // 2 violations

        java.util.function.Consumer d = (o ) -> { o.toString(); }; // 1 violation

        java.util.function.Consumer e = ( o) -> { o.toString(); }; // 1 violation

        java.util.stream.Stream.of().forEach(( o ) -> o.toString()); // 2 violations

        java.util.stream.Stream.of().forEach(( Object o ) -> o.toString()); // 2 violations

        java.util.stream.Stream.of().forEach(o -> o.toString( )); // 2 violations
    }

    void someMethod( String param ) { // 2 violations
    }
}
