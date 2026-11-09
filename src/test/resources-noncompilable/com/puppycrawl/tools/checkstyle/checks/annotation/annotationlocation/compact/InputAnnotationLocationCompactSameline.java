/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = false
allowSamelineParameterizedAnnotation = true
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF, \
         MODULE_DEF


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings("unused") int field1 = 0;

@Deprecated String field2 = "ok"; // violation 'Annotation 'Deprecated' should be alone on line.'

@SuppressWarnings("unused")
int field3 = 0;

@Deprecated
String field4 = "ok";

// violation below 'Annotation 'Deprecated' should be alone on line.'
@SuppressWarnings("unused") @Deprecated int field5 = 0;

@SuppressWarnings(value = "unused") void method1() {
}

@Deprecated void method2() { // violation 'Annotation 'Deprecated' should be alone on line.'
}

void main() {
    @Deprecated int local = 0;
    System.out.println(local + field1);
}

@SuppressWarnings("unused") record Point(int x, int y) {
    @Deprecated Point { // violation 'Annotation 'Deprecated' should be alone on line.'
    }
}

@Deprecated enum Color { // violation 'Annotation 'Deprecated' should be alone on line.'
    @Deprecated RED, // violation 'Annotation 'Deprecated' should be alone on line.'
    @SuppressWarnings("unused") GREEN
}
