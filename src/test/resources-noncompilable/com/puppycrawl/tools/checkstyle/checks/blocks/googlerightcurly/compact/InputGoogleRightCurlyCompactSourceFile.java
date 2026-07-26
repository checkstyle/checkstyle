/*
GoogleRightCurly
tokens = (default)LITERAL_IF, LITERAL_ELSE, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_DO, CLASS_DEF, INTERFACE_DEF, OBJBLOCK, RECORD_DEF, ANNOTATION_DEF, ENUM_DEF, \
         METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_DEFAULT, STATIC_INIT, INSTANCE_INIT, LITERAL_SYNCHRONIZED

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 0;
    boolean flag = true;
    if (flag) {
        a++;
    }
    else {
        a--;
    }
    // violation 4 lines above ''}' at column 5 should be on the same line as .*/else'
}
