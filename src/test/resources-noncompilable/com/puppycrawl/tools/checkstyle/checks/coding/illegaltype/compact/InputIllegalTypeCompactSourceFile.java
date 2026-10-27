/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, IMPORT, INTERFACE_DEF, METHOD_CALL, \
         METHOD_DEF, METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

void main() {
}

HashMap<String, String> concrete; // violation "Usage of type 'HashMap' is not allowed."
java.util.TreeSet<String> set; // violation "Usage of type 'java.util.TreeSet' is not allowed."
List<HashMap<String, String>> nested; // violation "Usage of type 'HashMap' is not allowed."
ArrayList<String> allowed;

HashMap<String, String> checked( // violation "Usage of type 'HashMap' is not allowed."
        HashMap<String, String> value) { // violation "Usage of type 'HashMap' is not allowed."
    return value;
}

void localTypes() {
    HashMap<String, String> local = null; // violation "Usage of type 'HashMap' is not allowed."
}

HashMap<String, String> getEnvironment(HashMap<String, String> value) {
    return value;
}

HashMap<String, String> getInitialContext(HashMap<String, String> value) {
    return value;
}

class Nested {
    HashMap<String, String> field; // violation "Usage of type 'HashMap' is not allowed."
}

record Entry(HashMap<String, String> value) { // violation "Usage of type 'HashMap' is not allowed."
}
