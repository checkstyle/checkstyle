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

public class InputLeftCurlyTestDefaultLambda
{ // violation
    static Runnable r1 = () -> { // ok
        String.valueOf("Hello world one!");
    };

    static Runnable r2 = () -> String.valueOf("Hello world two!");

    static Runnable r3 = () -> {String.valueOf("Hello world two!");}; // violation

    static Runnable r4 = () ->
    { // violation
        String.valueOf("Hello world one!");
    };
}
