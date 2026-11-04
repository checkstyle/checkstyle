/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.naming.illegalidentifiername;

public class InputXpathIllegalIdentifierNameInnerClass {
    class Inner {
        void test(int var) { // violation "Name 'var' must match pattern '^(?!var$|\S*\$)\S+$'."
            // do something
        }
    }
}
