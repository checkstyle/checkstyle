/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF,INTERFACE_DEF,ANNOTATION_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
           RECORD_DEF, COMPACT_CTOR_DEF
topSeparator = no_empty_line
bottomSeparator = no_empty_line

*/

// non-compiled with javac: Compilable with Java25

public class EmptyClass { // violation ''{' can not have empty line after.'

} // violation ''}' can not have empty line before.'

// violation below ''{' can not have empty line after.'
enum InputEmptyLineWrappingInBlockInterfaceAnnotationEnumEnum {

    A,
    B

} // violation ''}' can not have empty line before.'

// violation below ''{' can not have empty line after.'
@interface InputEmptyLineWrappingInBlockInterfaceAnnotationEnumAnnot {

    String value();

} // violation ''}' can not have empty line before.'

record Point(int x, int y) { // violation ''{' can not have empty line after.'

    Point { // violation ''{' can not have empty line after.'

        int a = 0;

    } // violation ''}' can not have empty line before.'

} // violation ''}' can not have empty line before.'

void main() {
    int a = 1;
}
