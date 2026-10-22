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

public boolean equals(Object other) { return false; }

public int hashCode() { return 0; }

public String toString() { return ""; }

public Object clone() { return null; }

public void finalize() { }

public boolean equals(String other) { return false; } // violation 'Redundant 'public' modifier.'

public boolean equals(Object first, Object second) { // violation 'Redundant 'public' modifier.'
    return false;
}

public boolean same(Object other) { return false; } // violation 'Redundant 'public' modifier.'

public String toString(int indent) { return ""; } // violation 'Redundant 'public' modifier.'

public static void main(String[] args) { } // violation 'Redundant 'public' modifier.'

void main() { }
