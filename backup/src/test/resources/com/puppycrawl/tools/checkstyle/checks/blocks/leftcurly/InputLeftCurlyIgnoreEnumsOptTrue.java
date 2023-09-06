/*
LeftCurly
option = (default)EOL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyIgnoreEnumsOptTrue {
    enum Colors {RED, // ok
        BLUE,
        GREEN;
        // violation below ''{' at column 44 should have line break after'
        @Override public String toString() { return ""; };
    }

    enum Languages {
        JAVA,
        PHP,
        SCALA,
        C,
        PASCAL
    }

    void method1(int a) {
        switch (a) {case 1: ; }
    }
}
