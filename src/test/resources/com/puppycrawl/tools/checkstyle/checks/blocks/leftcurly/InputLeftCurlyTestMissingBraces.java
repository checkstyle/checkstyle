/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyTestMissingBraces
{ // violation ''{' at column 1 should be on the previous line'
    /** @return helper func **/
    boolean condition()
    { // violation ''{' at column 5 should be on the previous line'
        return false;
    }

    /** Test do/while loops **/
    void testDoWhile()
    { // violation ''{' at column 5 should be on the previous line'
        // Valid
        do {
            testDoWhile();
        }
        while (condition());

        // Invalid
        do testDoWhile(); while (condition());
    }

    /** Test while loops **/
    void testWhile()
    { // violation ''{' at column 5 should be on the previous line'
        // Valid
        while (condition()) {
            testWhile();
        }

        // Invalid
        while(condition());
        while (condition())
            testWhile();
        while (condition())
            if (condition())
                testWhile();
    }

    /** Test for loops **/
    void testFor()
    { // violation ''{' at column 5 should be on the previous line'
        // Valid
        for (int i = 1; i < 5; i++) {
            testFor();
        }

        // Invalid
        for(int i = 1;i < 5;i++);
        for (int i = 1; i < 5; i++)
            testFor();
        for (int i = 1; i < 5;
             i++)
            if (i > 2)
                testFor();
    }

    /** Test if constructs **/
    public void testIf()
    { // violation ''{' at column 5 should be on the previous line'
        // Valid
        if (condition()) {
            testIf();
        }
        else if (condition()) {
            testIf();
        }
        else {
            testIf();
        }

        // Invalid
        if (condition());
        if (condition())
            testIf();
        if (condition())
            testIf();
        else
            testIf();
        if (condition())
            testIf();
        else {
            testIf();
        }
        if (condition()) {
            testIf();
        }
        else
            testIf();
        if (condition())
            if (condition())
                testIf();
    }

    void whitespaceAfterSemi()
    { // violation ''{' at column 5 should be on the previous line'
        //reject
        int i = 1;int j = 2;

        //accept
        for (;;) {
        }
    }

    /** Empty constructor block. **/
    public InputLeftCurlyTestMissingBraces() {}

    /** Empty method block. **/
    public void emptyImplementation() {}
}
