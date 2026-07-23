/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Annotation 'SuppressWarnings' should be alone on line.'
@SuppressWarnings("unused") int field1 = 0;

@Deprecated String field2 = "ok";

@SuppressWarnings("unused")
int field3 = 0;

@Deprecated
String field4 = "ok";

// 2 violations 3 lines below:
//    "Annotation 'SuppressWarnings' should be alone on line."
//    "Annotation 'Deprecated' should be alone on line."
@SuppressWarnings("unused") @Deprecated int field5 = 0;

@SuppressWarnings("unused")
    @Deprecated // violation '.* incorrect indentation level 4, .* should be 0.'
int field6 = 0;

void main() {
    @SuppressWarnings("unused") int local1 = 0;
    @Deprecated int local2 = 0;
    System.out.println(local1 + local2 + field1);
}

class Nested {
    // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
    @SuppressWarnings("unused") int nestedField1 = 0;

    @Deprecated String nestedField2 = "ok";

    @SuppressWarnings("unused")
    int nestedField3 = 0;

    void nestedMethod() {
        @SuppressWarnings("unused") int nestedLocal = 0;
        System.out.println(nestedLocal);
    }
}
