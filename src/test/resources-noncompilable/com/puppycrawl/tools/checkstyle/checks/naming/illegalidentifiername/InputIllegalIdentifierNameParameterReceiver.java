/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public record InputIllegalIdentifierNameParameterReceiver() {
    public void foo4(InputIllegalIdentifierNameParameterReceiver this) {
    }

    private class Inner {
        Inner(InputIllegalIdentifierNameParameterReceiver
                  InputIllegalIdentifierNameParameterReceiver.this) {
        }
    }
}
