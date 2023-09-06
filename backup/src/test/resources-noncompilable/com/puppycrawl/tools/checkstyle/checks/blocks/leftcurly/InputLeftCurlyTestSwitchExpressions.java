/*
LeftCurly
option = (default)EOL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestSwitchExpressions {
    int howMany1(int k) {
        switch (k)
        { // violation ''{' at column 9 should be on the previous line'
            case 1:
                { // violation ''{' at column 17 should be on the previous line'

            }

            case 2:
                { // violation ''{' at column 17 should be on the previous line'

            }

            case 3:
                { // violation ''{' at column 17 should be on the previous line'

            }
            default:
                {   // violation ''{' at column 17 should be on the previous line'

            }
        }
        return k;
    }

    int howMany2(int k) {
        return switch (k)
                { // violation ''{' at column 17 should be on the previous line'
            case 1 ->
                    { // violation ''{' at column 21 should be on the previous line'
                yield 2;
            }
            case 2 ->
                    { // violation ''{' at column 21 should be on the previous line'
                yield 3;
            }
            case 3 ->
                    { // violation ''{' at column 21 should be on the previous line'
                yield 4;
            }
            default ->
                    { // violation ''{' at column 21 should be on the previous line'
                yield k;
            }
        };
    }
}
