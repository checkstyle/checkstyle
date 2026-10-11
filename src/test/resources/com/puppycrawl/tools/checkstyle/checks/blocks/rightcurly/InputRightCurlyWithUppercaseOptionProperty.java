/*
RightCurly
option = alone
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyWithUppercaseOptionProperty {

    @SuppressWarnings("unused")
    private void foo2() { int a = 9; return; }
    // violation above ''}' at column 46 should be alone on a line'

    @SuppressWarnings("unused")
    private void foo3()
    { int var1 = 5; int var2 = 6; } // violation ''}' at column 35 should be alone on a line'

}
