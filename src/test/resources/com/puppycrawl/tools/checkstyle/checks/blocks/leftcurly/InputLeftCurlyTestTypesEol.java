/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, \
         INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, LITERAL_DEFAULT, \
         LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, LITERAL_IF, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, METHOD_DEF, \
         OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF, SWITCH_RULE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestTypesEol {

    // violation below ''{' at column 22 should have line break after'
    class InnerClass { int a;
    }

    // violation below ''{' at column 30 should have line break after'
    interface InnerInterface { int b = 1;
    }

    // violation below ''{' at column 26 should have line break after'
    record InnerRecord() { static int c;
    }

    // violation below ''{' at column 21 should have line break after'
    enum  InnerEnum { VALUE,
        VALUE2
    }

    // violation below ''{' at column 32 should have line break after'
    @interface InnerAnnotation { int d = 1;
    }
}
