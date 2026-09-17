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

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import java.util.HashMap;
import java.util.Map;

public class InputLeftCurlyAnonymousClassEol {

    Map<Integer, Integer> map = new HashMap<>() {{
        map.put(1, 2);
        map.put(1, 3);
    }};

    void method() {
        // violation below ''{' at column 38 should have line break after'
        Runnable r1 = new Runnable() { @Override
            public void run() {

            }
        };

        // violation 2 lines below ''{' at column 9 should be on the previous line'
        Runnable r2 = new Runnable()
        {
            @Override
            public void run() {

            }
        };

        Thread t = new Thread() {
            @Override
            public void run() {

            }
        };
    }
}
