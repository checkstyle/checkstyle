/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.naming.illegalidentifiername;

public class InputXpathIllegalIdentifierNameTwo {
    int foo(int te$t) { // violation "Name 'te$t' must match pattern '^(?!var$|\S*\$)\S+$'."
        return switch (te$t) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            default -> 5;
        };
    }
}
