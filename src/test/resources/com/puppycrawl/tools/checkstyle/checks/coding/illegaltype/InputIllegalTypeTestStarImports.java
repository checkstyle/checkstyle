/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = List
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.net.*;
import java.util.*;
import org.antlr.v4.runtime.*;

public class InputIllegalTypeTestStarImports
{
    List<Integer> l = new LinkedList<>(); // violation, 'Usage of type List is not allowed'.
}
