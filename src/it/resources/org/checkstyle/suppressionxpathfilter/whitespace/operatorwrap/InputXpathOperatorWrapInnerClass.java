/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, SR, BSR, \
         GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

package org.checkstyle.suppressionxpathfilter.whitespace.operatorwrap;

public class InputXpathOperatorWrapInnerClass {

    class Inner {
        void compute() {
            int y = 5 + // violation "'+' should be on a new line."
                7;
        }
    }
}
