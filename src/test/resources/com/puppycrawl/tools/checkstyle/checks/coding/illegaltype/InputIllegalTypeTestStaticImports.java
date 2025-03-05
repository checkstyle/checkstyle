/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = SomeStaticClass
legalAbstractClassNames = (default)
ignoredMethodNames = foo1
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import static com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.SomeStaticClass;
import java.lang.String;

public class InputIllegalTypeTestStaticImports
{
     private boolean foo(String s) {
         return true;
     }
     SomeStaticClass staticClass; // violation, 'Usage of type SomeStaticClass is not allowed'.
     private static SomeStaticClass foo1() { return null;}
     private static void foo2(SomeStaticClass s) {} // violation, 'Usage of type SomeStaticClass is not allowed'.
}
