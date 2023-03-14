/*
LeftCurly
option = NL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;
// violation below ''{' at column 57 should be on a new line'
public class InputLeftCurlyTestSwitchExpressionsNewLine {
    int howMany1(int k) { // violation ''{' at column 25 should be on a new line'
        switch (k)
        {
            case 1:
                {

            }

            case 2:
                {

            }

            case 3:
                {

            }
            default:
                {

            }
        }
        return k;
    }

    int howMany2(int k) { // violation ''{' at column 25 should be on a new line'
        return switch (k)
                {
            case 1 ->
                    {
                yield 2;
            }
            case 2 ->
                    {
                yield 3;
            }
            case 3 -> { // violation ''{' at column 23 should be on a new line'
                yield 4;
            }
            default ->
                    {
                yield k;
            }
        };
    }
}
