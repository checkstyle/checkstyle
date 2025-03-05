/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = Boolean[], Boolean[][]
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

public class InputIllegalTypeArrays {

    public Boolean[] array; // violation, 'Usage of type Boolean[] is not allowed'.

    public Boolean[][] matrix; // violation, 'Usage of type Boolean[][] is not allowed'.

    public Boolean[] getArray() { // violation, 'Usage of type Boolean[] is not allowed'.
        Boolean[] value = array != null ? array : new Boolean[0]; // violation, 'Usage of type Boolean[] is not allowed'.
        return value;
    }

    public Boolean[][] getMatrix() { // violation, 'Usage of type Boolean[][] is not allowed'.
        Boolean[][] value = matrix != null ? matrix : new Boolean[0][0]; // violation, 'Usage of type Boolean[][] is not allowed'.
        return value;
    }

}
