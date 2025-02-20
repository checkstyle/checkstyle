/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadLambdaWithSpace {
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

    void someMethod(String param) { // 2 violations
    }
}
