/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, \
         INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, LITERAL_DEFAULT, \
         LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, LITERAL_IF, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, METHOD_DEF, \
         OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF, SWITCH_RULE


*/

// non-compiled with javac: Compilable with Java25

void checkBraces(boolean flag)
{ // violation ''{' at column 1 should be on the previous line'
    if (flag) { System.out.println("same line"); }
    // violation above ''{' at column 15 should have line break after'
    if (flag) {
        System.out.println("end of line");
    }
    while (flag) {
        System.out.println("while");
        break;
    }
}

enum Colors {RED,
    BLUE,
    GREEN
}

void main() {}
