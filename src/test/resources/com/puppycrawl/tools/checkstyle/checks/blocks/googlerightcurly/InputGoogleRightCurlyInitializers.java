/*
GoogleRightCurly
tokens = (default)LITERAL_IF, LITERAL_ELSE, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_DO, CLASS_DEF, INTERFACE_DEF, OBJBLOCK, RECORD_DEF, ANNOTATION_DEF, ENUM_DEF, \
         METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_DEFAULT, STATIC_INIT, INSTANCE_INIT, LITERAL_SYNCHRONIZED

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyInitializers {

    private static int counter;

    private int value;

    static {
        counter = 0;}
    // violation above ''}' at column 21 should be alone on a line'

    static {
        counter = 1;
    }

    static {}

    {
        value = 0;}
    // violation above ''}' at column 19 should be alone on a line'

    {
        value = 1;}
    // violation above ''}' at column 19 should be alone on a line'

    {
    } {
    }
    // violation 2 lines above ''}' at column 5 should be alone on a line'
}
