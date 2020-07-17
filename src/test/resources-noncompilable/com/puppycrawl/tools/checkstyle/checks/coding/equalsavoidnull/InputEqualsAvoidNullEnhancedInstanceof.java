//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;
/* Config:
 *
 * allowEmptyConstructors  = false
 * allowEmptyMethods       = false
 * allowEmptyTypes         = false
 * allowEmptyLoops         = false
 * allowEmptyLambds        = false
 * allowEmptyCatche        = false
 * ignoreEnhancedForColon  = true
 * tokens = ASSIGN , BAND , BAND_ASSIGN , BOR , BOR_ASSIGN , BSR ,
 * BSR_ASSIGN , BXOR , BXOR_ASSIGN , COLON , DIV , DIV_ASSIGN ,
 * DO_WHILE , EQUAL , GE , GT , LAMBDA , LAND , LCURLY , LE ,
 * LITERAL_CATCH , LITERAL_DO , LITERAL_ELSE , LITERAL_FINALLY ,
 * LITERAL_FOR , LITERAL_IF , LITERAL_RETURN , LITERAL_SWITCH ,
 * LITERAL_SYNCHRONIZED , LITERAL_TRY , LITERAL_WHILE , LOR ,
 * LT , MINUS , MINUS_ASSIGN , MOD , MOD_ASSIGN , NOT_EQUAL ,
 * PLUS , PLUS_ASSIGN , QUESTION , RCURLY , SL , SLIST ,
 * SL_ASSIGN , SR , SR_ASSIGN , STAR , STAR_ASSIGN , LITERAL_ASSERT ,
 *  TYPE_EXTENSION_AND, PATTERN_VARIABLE_DEF .
 */
public class InputEqualsAvoidNullEnhancedInstanceof {
    public InputEqualsAvoidNullEnhancedInstanceof(String str) {
        if (str instanceof String myString) {
            System.out.println("Mystring!!");
            boolean myBool = myString.equals("MyString!!"); // violation
            boolean myOtherBool = myString.equals(str); // ok
        } else if (str instanceof String oneMoreString) {
            if(oneMoreString.equals("test")) { // violation
                System.out.println("Test!!");
            }
        }
    }

    void foo (Object string) {
        if(string instanceof String string1) {
            final boolean myBool = string1.equals("test1") // violation
                    || string1.equals("test1.5") // violation
                    || string1.equals("test2") || string1.equals("test3"); // violation 2x
            if(string instanceof Integer integer) {
                String string5 = integer.toString();
                if(integer.toString().equals("integer")) {

                }
            }
        }
    }
}
