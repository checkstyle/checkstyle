/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = com.PackageClass
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, IMPORT, INTERFACE_DEF, METHOD_CALL, \
         METHOD_DEF, METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF


*/

// non-compiled with javac: contains specially crafted set of imports
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import com.PackageClass.*;

public class InputIllegalTypePackageClassName {
    PackageClass o = new PackageClass();
}
