/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = (default)false
ignoreEnhancedForColon = (default)true
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, \
         LAMBDA, LAND, LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TR \
          Y, LITERAL_WHILE, LOR, \
         LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, NOT_EQUAL, PLUS, PLUS_ASSIGN, \
         QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, SR_ASSIGN, STAR, \
         STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND, LITERAL_WHEN

*/


package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundRecords {
    // simple record def
    record MyRecord() {}
    // 2 violations above:
    // ''{' is not followed by whitespace.'
    // ''}' is not preceded with whitespace.'

    // simple record def
    record MyRecord1() {
    }

    // nested constructs
    record MyRecord2() {
        class MyClass {}
        // 2 violations above:
        // ''{' is not followed by whitespace.'
        // ''}' is not preceded with whitespace.'
        interface Foo {}
        // 2 violations above:
        // ''{' is not followed by whitespace.'
        // ''}' is not preceded with whitespace.'
        record MyRecord () {}
        // 2 violations above:
        // ''{' is not followed by whitespace.'
        // ''}' is not preceded with whitespace.'
    }

    // method
    record MyRecord3() {
        void method (){ // violation ''{' is not preceded with whitespace'
            final int a = 1;
            int b= 1; // violation ''=' is not preceded with whitespace'
            b=1;
            // 2 violations above:
            // ''=' is not followed by whitespace.'
            // ''=' is not preceded with whitespace.'
        }

    }

    // ctor
    record MyRecord4() {
        public MyRecord4() {
            final int a = 1;
            int b= 1; // violation ''=' is not preceded with whitespace'
            b=1;
            // 2 violations above:
            // ''=' is not followed by whitespace.'
            // ''=' is not preceded with whitespace.'
        }
    }

    // compact ctor
    record MyRecord5() {
        public MyRecord5 {
            final int a = 1;
            int b= 1; // violation ''=' is not preceded with whitespace'
            b=1;
            // 2 violations above:
            // ''=' is not followed by whitespace.'
            // ''=' is not preceded with whitespace.'
        }
    }

    // static fields
    record MyRecord6() {
        static final int a = 1;
        static int b= 1; // violation ''=' is not preceded with whitespace'
    }

    record TestRecord7() {
        public TestRecord7 {}
        // 2 violations above:
        // ''{' is not followed by whitespace.'
        // ''}' is not preceded with whitespace.'
    }

    record TestRecord8() {
        public TestRecord8 () {
        }
    }

    class TestClass {
        public TestClass () {
        }
    }

    record TestRecord9() {
        public TestRecord9() {
        }
    }

    class TestClass2 {
        public TestClass2() { }
    }
}
