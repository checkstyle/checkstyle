/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

// non-compiled with javac: Compilable with Java25

// violation below, 'Annotation 'SuppressWarnings' must be on a separate line from target.'
@SuppressWarnings("unused") int field1 = 0;

// violation below, 'Annotation 'Deprecated' must be on a separate line from target.'
@Deprecated String field2 = "ok";

@SuppressWarnings("unused")
int field3 = 0;

@Deprecated
String field4 = "ok";

// 2 violations 3 lines below:
//    "Annotation 'SuppressWarnings' must be on a separate line from target."
//    "Annotation 'Deprecated' must be on a separate line from target."
@SuppressWarnings("unused") @Deprecated int field5 = 0;

void main() {
    @SuppressWarnings("unused") int local1 = 0;
    @Deprecated int local2 = 0;
    System.out.println(local1 + local2 + field1);
}

@Deprecated void helperMethod() {}

@Helper @Deprecated
void helperMethodOne() {}

@Helper
@Deprecated
void helperMethodTwo() {}

// violation 2 lines below 'Annotation 'Special' must be alone on a line, or all on one line.'
@Helper @Deprecated
@Special
void helperMethodThree() {}

@interface Helper {}
@interface Special {}

class Nested {
    // violation below, 'Annotation 'SuppressWarnings' must be on a separate line from target.'
    @SuppressWarnings("unused") int nestedField1 = 0;

    @SuppressWarnings("unused")
    int nestedField3 = 0;

    void nestedMethod() {
        @SuppressWarnings("unused") int nestedLocal = 0;
        System.out.println(nestedLocal);
    }
}
