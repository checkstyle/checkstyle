/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = java.util.ArrayList
legalAbstractClassNames = (default)
ignoredMethodNames = ignored
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = LITERAL_PUBLIC
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, IMPORT, INTERFACE_DEF, METHOD_CALL, \
         METHOD_DEF, METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.ArrayList;
import java.util.HashMap;

void main() {
}

public ArrayList<String> publicList; // violation "Usage of type 'ArrayList' is not allowed."
// violation below "Usage of type 'java.util.ArrayList' is not allowed."
public java.util.ArrayList<String> qualified;
private ArrayList<String> privateList;
public HashMap<String, String> allowed;

public ArrayList<String> checked( // violation "Usage of type 'ArrayList' is not allowed."
        ArrayList<String> value) { // violation "Usage of type 'ArrayList' is not allowed."
    return value;
}

private ArrayList<String> unchecked(ArrayList<String> value) {
    return value;
}

public ArrayList<String> ignored(ArrayList<String> value) {
    return value;
}

public ArrayList<String> getEnvironment( // violation "Usage of type 'ArrayList' is not allowed."
        ArrayList<String> value) { // violation "Usage of type 'ArrayList' is not allowed."
    return value;
}

// violation below "Usage of type 'ArrayList' is not allowed."
private record Entry(ArrayList<String> value) {
}
