/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = HashMap
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.HashMap;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class InputIllegalTypeNewArrayStructure {
    void method(int x) {
        int numberOfTests = x + 9;
        if (x > 7) {
            HashMap<String, KeyValue>[] kvMaps = new HashMap[numberOfTests];
            // violation above, 'Usage of type 'HashMap' is not allowed'
        }
    }

}
