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

class InputLeftCurlyTestDefault3
{ // violation ''{' at column 1 should be on the previous line'
}

/**
 * False positive
 *
 */
class Absent_CustomFieldSerializer3 {

    public static void serialize() {} // Expected nothing but was "'}' should be alone on a line."
}

class Absent_CustomFieldSerializer4
{ // violation ''{' at column 1 should be on the previous line'
    public Absent_CustomFieldSerializer4() {}
}

class EmptyClass2 {}

interface EmptyInterface3 {}
