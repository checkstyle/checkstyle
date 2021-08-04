/*
IllegalIdentifierName
format = (default)(?i)^(?!(record|yield|var|permits|sealed|_)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public record InputIllegalIdentifierNameParameterReceiver() { // ok
    public void foo4(InputIllegalIdentifierNameParameterReceiver this) { // ok
    }

    private class Inner {
        Inner(InputIllegalIdentifierNameParameterReceiver // ok
                  InputIllegalIdentifierNameParameterReceiver.this) { // ok
        }
    }
}
