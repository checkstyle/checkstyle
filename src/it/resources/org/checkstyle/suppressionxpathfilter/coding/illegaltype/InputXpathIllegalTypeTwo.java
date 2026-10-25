/*
IllegalType
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
illegalClassNames = Boolean
legalAbstractClassNames = (default)
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, IMPORT, INTERFACE_DEF, METHOD_CALL, \
         METHOD_DEF, METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF
validateAbstractClassNames = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltype;

import java.io.Serializable;

public class InputXpathIllegalTypeTwo {
 // violation 2 lines below """Declaring variables, return values or
 //     parameters of type 'Boolean' is not allowed."""
 public <T extends Boolean, U extends Serializable> void typeParam(T a) {}
}
