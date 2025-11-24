/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadForWhitespace2
{
    void method1()
    {
        for (int i = 0; i < 1; i++) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        }

        for (int i = 0; i < 1;i++) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        }

        for (int i = 0; i < 1;i++ ) { // violation, ''(' is not followed by whitespace.'
        }

        for (int i = 0; i < 1; i++ ) { // violation, ''(' is not followed by whitespace.'
        }

        for (int i = 0; i < 1;) { // violation, ''(' is not followed by whitespace.'
            i++;
        }

        for (int i = 0; i < 1; ) { // violation, ''(' is not followed by whitespace.'
            i++;
        }

        // test eol, there is no space after second SEMI
        for (int i = 0; i < 1; // violation, ''(' is not followed by whitespace.'
            ) {
            i++;
        }
    }

    void method2()
    {
        for ( int i = 0; i < 1; i++ ) {
        }

        for ( int i = 0; i < 1; ) {
            i++;
        }

        int i = 0;
        for ( ; i < 1; i++ ) {
        }

        for (; i < 2; i++ ) {
        }

        for (
        ;; ) {
        }
    }
}
