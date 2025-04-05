/*
IllegalType
validateAbstractClassNames = true
illegalAbstractClassNameFormat = Gitt
legalAbstractClassNames = (default)
memberModifiers = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
tokens = (default)ANNOTATION_FIELD_DEF,CLASS_DEF,INTERFACE_DEF,METHOD_CALL,METHOD_DEF,METHOD_REF, \
         PARAMETER_DEF,VARIABLE_DEF,PATTERN_VARIABLE_DEF,RECORD_DEF,RECORD_COMPONENT_DEF, \
         RECORD_PATTERN_DEF
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

public class InputIllegalTypeAbstractClassNameFormat {

}

class Test extends Gitter { // violation 'Usage of type 'Gitter' is not allowed'
}

class Test1 extends Github {
}

class Gitter{}
class Github{}
