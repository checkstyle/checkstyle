/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, \
         RECORD_DEF, RECORD_PATTERN_DEF


*/

// non-compiled with javac: Compilable with Java25

void method(int value) {
}

void main() {
    int value = (1 + 2);
    method( value); // violation "'(' is followed by whitespace"
    method(value ); // violation "')' is preceded with whitespace"
    if (value > 0) {
        method(value);
    }
}
