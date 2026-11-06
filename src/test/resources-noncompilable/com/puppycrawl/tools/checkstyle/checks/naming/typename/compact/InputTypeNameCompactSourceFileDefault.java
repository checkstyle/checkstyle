/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class GoodName {
}

class badName { // violation 'Name 'badName' must match pattern'
}

public static class Bad_Name { // violation 'Name 'Bad_Name' must match pattern'
}

private static class lowerCase { // violation 'Name 'lowerCase' must match pattern'
}

interface badInterface { // violation 'Name 'badInterface' must match pattern'
}

enum badEnum { // violation 'Name 'badEnum' must match pattern'
    A, B
}

record badRecord(int value) { // violation 'Name 'badRecord' must match pattern'
}

@interface badAnnotation { // violation 'Name 'badAnnotation' must match pattern'
}
