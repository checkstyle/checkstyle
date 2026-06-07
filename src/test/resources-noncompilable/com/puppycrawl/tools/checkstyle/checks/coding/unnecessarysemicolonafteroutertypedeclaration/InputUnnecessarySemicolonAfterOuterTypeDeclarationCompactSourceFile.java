/*
UnnecessarySemicolonAfterOuterTypeDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

class MemberClass {

};

interface MemberInterface {

};

enum MemberEnum {

};

@interface MemberAnnotation {

};

record MemberRecord() {

};

class OuterWithNested {

    class Nested {

    };

    enum NestedEnum {

    };

};

class CleanClass {}

interface CleanInterface {}

enum CleanEnum {}

@interface CleanAnnotation {}

record CleanRecord() {}

void main() {
    class LocalClass {

    };
    System.out.println("Hello!");
}
