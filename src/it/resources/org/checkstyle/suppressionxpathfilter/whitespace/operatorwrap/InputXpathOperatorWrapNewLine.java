/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

package org.checkstyle.suppressionxpathfilter.whitespace.operatorwrap;

public class InputXpathOperatorWrapNewLine {

    void test() {
        int x = 1 + // violation ''\+' should be on a new line.'
             2
             -
             3
             -
             4;
     x = x + 2;

   }

   void test2() {
        int x = 1
             +
             2
             -
             3
             -
             4;
     x = x + 2;

   }
}
