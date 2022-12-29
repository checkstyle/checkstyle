/*
LeftCurly
option = eol
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;
public class InputLeftCurlyHasLineBreakAfterWithComment {

    void method() { // ok
    }

    void method1() { /* cmt */ int a; // violation ''{' at column 20 should have line break after'

    }

    void method2() { /* CheckStyle */
        int a;
    }

    void method3() {/* comment */}

    void method4() { /*****/
    }

    void method5() {} // comment

    void method6() { // ok
        int a;
    }

    void noMethod() {
    }

    void method7() { int s; // violation ''{' at column 20 should have line break after'

    }
}
