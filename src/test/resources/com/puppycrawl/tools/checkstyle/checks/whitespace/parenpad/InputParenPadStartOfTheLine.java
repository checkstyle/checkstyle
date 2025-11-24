/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadStartOfTheLine {
public String checkSmth( String
a) { // violation, '')' is not preceded with whitespace.'
    return a + 1;
   }
}
