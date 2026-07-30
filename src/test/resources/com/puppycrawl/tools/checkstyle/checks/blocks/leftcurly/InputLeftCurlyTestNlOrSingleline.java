/*
LeftCurly
option = NL_OR_SINGLELINE
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, \
         INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, LITERAL_DEFAULT, \
         LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, LITERAL_IF, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, METHOD_DEF, \
         OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF, SWITCH_RULE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestNlOrSingleline
{

    class SingleLineOk { }

    class SingleNextLineOk
    { }

    class MultiLineOk
    {
    }

    class MultiLineFail { // violation ''{' at column 25 should be on a new line'
    }

    void singleLineOk() { }

    void singleNextLineOk()
    { }

    void multiLineOk()
    {
    }

    void multiLineFail() { // violation ''{' at column 26 should be on a new line'
    }

    boolean x;

    void singleLineStatementOk() { x = true; }

    void singleNextLineStatementOk()
    { x = true; }

    boolean multiStatementSplitLinesFail() { // violation ''{' at column 44 should be on a new line'
        x = true;
        return x;
    }

    boolean multiStatementTrailingLeftCurlyFail() { // violation ''{' at column 51 should be on a new line'
        x = true;
        return x;
    }

    void tryCatchFinallyOk()
    {
        try { doNothing(); }
        catch (Exception e) { doNothing(); }
        finally { doNothing(); }
    }

    void doNothing() { }
}
