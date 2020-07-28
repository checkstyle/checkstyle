//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
/* Config:
 * allowEmptyConstructors = true
 * allowEmptyMethods = false
 * allowEmptyTypes = false
 * allowEmptyLoops = false
 * allowEmptyLambdas = false
 * allowEmptyCatches = false
 * ignoreEnhancedForColon = true
 * tokens = {ASSIGN , BAND , BAND_ASSIGN , BOR , BOR_ASSIGN , BSR , BSR_ASSIGN , BXOR ,
 *  BXOR_ASSIGN , COLON , DIV , DIV_ASSIGN , DO_WHILE , EQUAL , GE , GT , LAMBDA , LAND , LCURLY ,
 *  LE , LITERAL_CATCH , LITERAL_DO , LITERAL_ELSE , LITERAL_FINALLY , LITERAL_FOR , LITERAL_IF ,
 *  LITERAL_RETURN , LITERAL_SWITCH , LITERAL_SYNCHRONIZED , LITERAL_TRY , LITERAL_WHILE , LOR ,
 *  LT , MINUS , MINUS_ASSIGN , MOD , MOD_ASSIGN , NOT_EQUAL , PLUS , PLUS_ASSIGN , QUESTION ,
 *  RCURLY , SL , SLIST , SL_ASSIGN , SR , SR_ASSIGN , STAR , STAR_ASSIGN , LITERAL_ASSERT ,
 *  TYPE_EXTENSION_AND}
 */
public class InputWhitespaceAroundAllowEmptyCompactCtors {
    //simple record def
    record MyRecord() {} // violation x2

    // simple record def
    record MyRecord1() {
    } // ok

    // nested constructs
    record MyRecord2() {
        class MyClass {} // violation 2x
        interface Foo {} // violation 2x
        record MyRecord () {} // violation x2
    }

    // method
    record MyRecord3() {
        void method (){ // violation
            final int a = 1;
            int b= 1; // violation
            b=1; // violation x2
        }

    }

    // ctor
    record MyRecord4() {
        public MyRecord4() {
            final int a = 1;
            int b= 1; // violation
            b=1; // violation x2
        }
    }

    // compact ctor
    record MyRecord5() {
        public MyRecord5 {
        } // ok
    }

    // static fields
    record MyRecord6() {
        static final int a = 1;
        static int b= 1; // violation
    }

    record TestRecord7() {
        public TestRecord7 {} // ok
    }
}
