/*
MethodParamPad
allowLineBreaks = (default)false
option = (default)nospace
tokens = (default)CTOR_DEF, CTOR_CALL, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF, RECORD_PATTERN_DEF


*/

package org.checkstyle.suppressionxpathfilter.whitespace.methodparampad;

public class InputXpathMethodParamPadTwo {
    public void sayHello
            (String name) { // violation "'(' should be on the previous line."

    }
}
