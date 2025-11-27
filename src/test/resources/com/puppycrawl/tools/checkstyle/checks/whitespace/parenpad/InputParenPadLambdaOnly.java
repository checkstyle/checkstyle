/*
ParenPad
option = (default)nospace
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadLambdaOnly {
    {
        java.util.function.Consumer a = (o) -> { o.toString(); };

        java.util.function.Consumer b = o -> { o.toString(); };

        java.util.function.Consumer c = ( o ) -> { o.toString(); };
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'

        java.util.function.Consumer d = (o ) -> { o.toString(); };
        // violation above, '')' is preceded with whitespace.'

        java.util.function.Consumer e = ( o) -> { o.toString(); };
        // violation above, ''(' is followed by whitespace.'

        java.util.stream.Stream.of().forEach(( o ) -> o.toString());
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'

        java.util.stream.Stream.of().forEach(( Object o ) -> o.toString());
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'

        java.util.stream.Stream.of().forEach(o -> o.toString( ));
    }

    void someMethod( String param ) {
    }
}
