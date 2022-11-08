/*
LeftCurly
option = eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyNew {

    void method1 ()
    /* violation not reported */ { } // violation ''{' at column 34 should be on the previous line'

    void method2 ()
    { } // violation ''{' at column 5 should be on the previous line'

    void method3() {
        if(4>2)
            /* I am emoji 🤣  */ { // violation ''{' at column 33 should be on the previous line'
        }
    }

    InputLeftCurlyNew()
    /* I am comment */ { // violation ''{' at column 24 should be on the previous line'
        System.out.println("Hello CheckStyle");
    }

    InputLeftCurlyNew(String name)
    { // violation ''{' at column 5 should be on the previous line'
        System.out.println("I am int" + name);
    }

    InputLeftCurlyNew(int a) { // ok
    }
}
