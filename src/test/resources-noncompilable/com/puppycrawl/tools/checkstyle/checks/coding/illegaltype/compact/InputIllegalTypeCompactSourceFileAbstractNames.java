/*
IllegalType
validateAbstractClassNames = true
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = BaseAllowed
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = ^(.*[.])?Base.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, IMPORT, INTERFACE_DEF, METHOD_CALL, \
         METHOD_DEF, METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.HashMap;

void main() {
}

BaseForbidden forbidden; // violation "Usage of type 'BaseForbidden' is not allowed."
BaseAllowed allowed;
java.util.AbstractList<String> allowedByPattern;
HashMap<String, String> concrete; // violation "Usage of type 'HashMap' is not allowed."

BaseForbidden checked( // violation "Usage of type 'BaseForbidden' is not allowed."
        BaseForbidden value) { // violation "Usage of type 'BaseForbidden' is not allowed."
    return value;
}

abstract class BaseForbidden {
}

abstract class BaseAllowed {
}
