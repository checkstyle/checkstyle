/*
ParenPad
option = space
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, \
         RECORD_DEF, RECORD_PATTERN_DEF


*/

package org.checkstyle.suppressionxpathfilter.whitespace.parenpad;

public class InputXpathParenPadRightNotPreceded{
    void method() {
        if ( false) { // violation "')' is not preceded with whitespace."
        }
        if ( true ) {
        }
    }
}
