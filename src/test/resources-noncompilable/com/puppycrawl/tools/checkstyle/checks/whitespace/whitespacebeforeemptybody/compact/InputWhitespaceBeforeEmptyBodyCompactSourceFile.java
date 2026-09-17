/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, CLASS_DEF, INTERFACE_DEF, \
         RECORD_DEF, ENUM_DEF, ANNOTATION_DEF, LITERAL_NEW, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_SYNCHRONIZED, LITERAL_SWITCH, LAMBDA, LITERAL_DO, LITERAL_IF, LITERAL_ELSE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1;
}

// violation below ''{' is not preceded with whitespace'
class EmptyClass{}

// violation below ''{' is not preceded with whitespace'
interface EmptyInterface{}

// violation below ''{' is not preceded with whitespace'
enum EmptyEnum{}

// violation below ''{' is not preceded with whitespace'
void sum(int... a){}
