/*
WhitespaceAround
allowEmptyConstructors = true
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
allowEmptySwitchBlockStatements = (default)false
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundAllowEmptyCompactCtors {
    //simple record def
    record MyRecord() {} // 2 violations

    // simple record def
    record MyRecord1() {
    }

    // nested constructs
    record MyRecord2() {
        class MyClass {} // 2 violations
        interface Foo {} // 2 violations
        record MyRecord () {} // 2 violations
    }

    // method
    record MyRecord3() {
        void method (){ // violation ''{' is not preceded with whitespace'
            final int a = 1;
            int b= 1; // violation ''=' is not preceded with whitespace'
            b=1; // 2 violations
        }

    }

    // ctor
    record MyRecord4() {
        public MyRecord4() {
            final int a = 1;
            int b= 1; // violation ''=' is not preceded with whitespace'
            b=1; // 2 violations
        }
    }

    // compact ctor
    record MyRecord5() {
        public MyRecord5 {
        }
    }

    // static fields
    record MyRecord6() {
        static final int a = 1;
        static int b= 1; // violation ''=' is not preceded with whitespace'
    }

    record TestRecord7() {
        public TestRecord7 {}
    }
}

class foo {
    public foo() {}
}

class foo2 {
    public foo2(){} // violation ''{' is not preceded with whitespace'
}

class foo3 {
    public foo3(){System.out.println();} // 3 violations
                                    //-^--- violation, no WS before '}'
}

record TestRecord8(int a, int b) {
    public TestRecord8{} // violation ''{' is not preceded with whitespace'
}
