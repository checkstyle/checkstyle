//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

/* Config:
 *
 * tokens = {EXPR , IDENT , NUM_DOUBLE , NUM_FLOAT , NUM_INT , NUM_LONG , STRING_LITERAL ,
 *  LITERAL_NULL , LITERAL_FALSE , LITERAL_TRUE , ASSIGN , BAND_ASSIGN , BOR_ASSIGN ,
 *  BSR_ASSIGN , BXOR_ASSIGN , DIV_ASSIGN , MINUS_ASSIGN , MOD_ASSIGN , PLUS_ASSIGN ,
 *  SL_ASSIGN , SR_ASSIGN , STAR_ASSIGN , LAMBDA, TEXT_BLOCK_BEGIN}
 */
public class InputUnnecessaryParenthesesCheckTextBlocks {
    void method() {
        String string1 = ("this") + ("that") + ("other"); // violation
        String string2 = ("""
                this""")
                + ("""
                that""")
                + ("""
                other"""); // should be violation
        String string3 = ("""
                this is a test.""") + ("""
                and another line""");
    }
}
