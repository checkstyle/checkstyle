/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

// violation below, 'Annotation 'SuppressWarnings' should be alone on line.'
@SuppressWarnings("unused") int field1 = 0;

@Deprecated String field2 = "ok"; // ok, single parameterless annotation on same line

@SuppressWarnings("unused")
int field3 = 0; // ok, annotation alone on its own line

@Deprecated
String field4 = "ok"; // ok, annotation alone on its own line

// 2 violations 3 lines below:
//    "Annotation 'SuppressWarnings' should be alone on line."
//    "Annotation 'Deprecated' should be alone on line."
@SuppressWarnings("unused") @Deprecated int field5 = 0;

@SuppressWarnings("unused")
    @Deprecated // violation '.* incorrect indentation level 4, .* should be 0.'
int field6 = 0;

void main() {
    @SuppressWarnings("unused") int local1 = 0; // ok, local variable is out of scope
    @Deprecated int local2 = 0; // ok, local variable is out of scope
    System.out.println(local1 + local2 + field1);
}

class Nested {
    // violation below, 'Annotation 'SuppressWarnings' should be alone on line.'
    @SuppressWarnings("unused") int nestedField1 = 0;

    @Deprecated String nestedField2 = "ok"; // ok, single parameterless on same line

    @SuppressWarnings("unused")
    int nestedField3 = 0; // ok, annotation alone on its own line

    void nestedMethod() {
        @SuppressWarnings("unused") int nestedLocal = 0; // ok, local variable
        System.out.println(nestedLocal);
    }
}
