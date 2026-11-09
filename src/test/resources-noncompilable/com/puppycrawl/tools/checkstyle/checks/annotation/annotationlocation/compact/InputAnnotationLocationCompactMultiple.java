/*
AnnotationLocation
allowSamelineMultipleAnnotations = true
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF, \
         MODULE_DEF


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings("unused") int field1 = 0;

@SuppressWarnings("unused") @Deprecated int field2 = 0;

@SuppressWarnings("unused")
    @Deprecated // violation '.* incorrect indentation level 4, .* should be 0.'
int field3 = 0;

@SuppressWarnings("unused") @Deprecated void method() {
}

void main() {
    System.out.println(field1 + field2 + field3);
}

@SuppressWarnings("unused") @Deprecated class Nested {
    @SuppressWarnings("unused") @Deprecated int nestedField = 0;
}
