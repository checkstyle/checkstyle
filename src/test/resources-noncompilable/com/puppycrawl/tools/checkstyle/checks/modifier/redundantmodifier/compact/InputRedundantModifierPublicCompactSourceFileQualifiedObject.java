/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

// non-compiled with javac: Compilable with Java25
@Override
public boolean equals(final java.lang.Object other) { return false; }

public boolean equals(java.lang.String other) { // violation 'Redundant 'public' modifier.'
    return false;
}

void main() { }
