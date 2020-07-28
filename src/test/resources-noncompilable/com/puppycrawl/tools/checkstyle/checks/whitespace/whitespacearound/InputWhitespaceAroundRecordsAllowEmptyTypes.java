//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
/* Config:
 * allowEmptyConstructors = false
 * allowEmptyMethods = false
 * allowEmptyTypes = true
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
public class InputWhitespaceAroundRecordsAllowEmptyTypes {
    // simple record def
    record MyRecord() {} // ok

    // simple record def
    record MyRecord1() {
    } // ok

    // nested constructs
    record MyRecord2() {
        class MyClass {} // ok
        interface Foo {} // ok
        record MyRecord () {} // ok
    }
}
