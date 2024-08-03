/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = A, B, C, D
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

class D { }

// violation below, 'Usage of type 'D' is not allowed.'
public class InputIllegalTypeInPermitsList extends D {

    sealed class D permits B, E { } // ok, We don't ban the instantiation of illegal types

    // violation below, 'Usage of type 'D' is not allowed.'
    sealed class E extends D permits C { }

    sealed class C extends E permits A { }

    // violation below, 'Usage of type 'C' is not allowed.'
    final class A extends C { }

    // violation below, 'Usage of type 'D' is not allowed.'
    final class B extends D { }
}
