/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         RECORD_DEF, COMPACT_CTOR_DEF

*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings("unused") @Deprecated int field1 = 0;

@Deprecated String field2 = "ok";

@SuppressWarnings("unused")
int field3 = 0;

@Deprecated
String field4 = "ok";

@Deprecated
@SuppressWarnings("unused") int badfield6 = 0;
// violation above 'Annotations must be on a separate line from 'badfield6'.'

@Deprecated @SuppressWarnings("unused") int goodfield6;

@Deprecated
@SuppressWarnings("unused")
int good;

// violation below 'Annotations must be on a separate line from 'badfield7'.'
@Deprecated int
        badfield7;

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

@Helper @Deprecated
@Special
void helperMethodThree() {}
// violation above """Annotations on 'helperMethodThree' must be all on one line or
// all on separate lines."""

@Helper @Deprecated @Special void helperMethodFour() {}

@interface Helper {}
@interface Special {}

class Nested {
    @SuppressWarnings("unused") int nestedField1 = 0;

    @SuppressWarnings("unused")
    int nestedField3 = 0;

    void nestedMethod() {
        @SuppressWarnings("unused") int nestedLocal = 0;
        System.out.println(nestedLocal);
    }
}
