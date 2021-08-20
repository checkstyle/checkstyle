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
        { // violation
            case 1:
                { // violation

            }

            case 2:
                { // violation

            }

            case 3:
                { // violation

            }
            default:
                {   // violation

            }
        }
        return k;
    }

    int howMany2(int k) {
        return switch (k)
                { // violation
            case 1 ->
                    {
                yield 2; // violation above
            }
            case 2 ->
                    {
                yield 3; // violation above
            }
            case 3 ->
                    {
                yield 4; // violation above
            }
            default ->
                    {
                yield k; // violation above
            }
        };
    }
}
