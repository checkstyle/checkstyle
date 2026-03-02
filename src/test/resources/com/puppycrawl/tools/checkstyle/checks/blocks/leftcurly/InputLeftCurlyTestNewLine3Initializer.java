/*
LeftCurly
option = nl
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyTestNewLine3Initializer
{
    // Test static initialiser
    static
    {
        int x = 1; // should not require any javadoc
    }
}

class ClassWithStaticInitializersTestNewLine3
{
    static { // violation ''{' at column 12 should be on a new line'
    }
    static
    {}

    static class Inner
    {
        static { // violation ''{' at column 16 should be on a new line'
            int i = 1;
        }
    }
}
