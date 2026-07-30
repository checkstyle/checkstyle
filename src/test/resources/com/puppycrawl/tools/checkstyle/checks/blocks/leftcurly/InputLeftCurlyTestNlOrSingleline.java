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

    class MultiLineViolation { // violation ''{' at column 30 should be on a new line'
    }

    void singleLineOk() { }

    void singleNextLineOk()
    { }

    void multiLineOk()
    {
    }

    void multiLineViolation() { // violation ''{' at column 31 should be on a new line'
    }

    boolean flag;

    void singleLineStatementOk() { flag = true; }

    void singleNextLineStatementOk()
    { flag = true; }

    // violation below ''{' at column 42 should be on a new line'
    boolean statementsSplitAcrossLines() { flag = true;
        return flag;
    }

    // violation below ''{' at column 47 should be on a new line'
    boolean statementsWithTrailingLeftCurly() {
        flag = true;
        return flag;
    }

    void nestedSingleLineBlocks()
    {
        try { doNothing(); }
        catch (Exception exc) { doNothing(); }
        finally { doNothing(); }
    }

    int singleLineSwitch(int value) { return switch (value) { default -> 0; }; }

    void doNothing() { }
}
