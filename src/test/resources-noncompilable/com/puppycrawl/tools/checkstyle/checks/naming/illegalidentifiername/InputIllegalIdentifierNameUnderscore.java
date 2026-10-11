/*
IllegalIdentifierName
format = (?i)^(?!(record|yield|var|permits|sealed|_)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNameUnderscore {
    String _string = "_string";
    String string_ = "string_";
    String another_string = "string";
    // violation below 'Name '_' must match pattern'
    String _;
}
