/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

// non-compiled with javac: Compilable with Java25
public void myMethod() { } // violation 'Redundant 'public' modifier.'

public static int helper() { return 0; } // violation 'Redundant 'public' modifier.'

static void util() { }

private void hidden() { }

class Nested {
    public void nestedMethod() { }
}

void main() { }
