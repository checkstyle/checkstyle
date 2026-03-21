/*
com.puppycrawl.tools.checkstyle.filters.SuppressionXpathFilter
file = (file)InputTreeWalkerSuppressionXpathFilterAbsolute.xml

com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck
ignoreEnums = (default)true
option = (default)eol
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSuppressionXpathFilterAbsolute {
    public void test()
    {
        int a = 1;
        int b = 2;
        int c = a + b;
    }
}
