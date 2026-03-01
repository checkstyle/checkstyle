/*
EmptyLineWrappingInBlock
tokens = (default) CLASS_DEF,INTERFACE_DEF,ANNOTATION_DEF,ENUM_DEF,ENUM_CONSTANT_DEF,
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

// non-compiled with javac: Compilable with Java25

// violation 2 lines below ''{' must have exactly one empty line after.'
// violation below''}' must have exactly one empty line before'
public class EmptyClass { }

enum InputEmptyLineWrappingInBlockInterfaceAnnotationEnumEnum {
    A, // violation above ''{' must have exactly one empty line after.'
    B
} // violation ''}' must have exactly one empty line before'

@interface InputEmptyLineWrappingInBlockInterfaceAnnotationEnumAnnot {
    String value(); // violation above ''{' must have exactly one empty line after.'
} // violation ''}' must have exactly one empty line before'

void main() {
    int a = 1;
}
