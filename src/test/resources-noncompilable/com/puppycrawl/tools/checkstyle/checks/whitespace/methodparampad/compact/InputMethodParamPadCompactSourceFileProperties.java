/*
MethodParamPad
allowLineBreaks = true
option = space
tokens = (default)CTOR_DEF, CTOR_CALL, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF, RECORD_PATTERN_DEF


*/

// non-compiled with javac: Compilable with Java25

void method() { // violation "'(' is not preceded with whitespace"
}

void method2 () {
}

void method3
        (int value) {
}

void main () {
}
