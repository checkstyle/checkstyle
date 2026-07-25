/*
GoogleRightCurly
tokens = (default)LITERAL_IF, LITERAL_ELSE, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_DO, CLASS_DEF, INTERFACE_DEF, OBJBLOCK, RECORD_DEF, ANNOTATION_DEF, \
         METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_DEFAULT, STATIC_INIT, INSTANCE_INIT, LITERAL_SYNCHRONIZED

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

import java.util.Comparator;

public class InputGoogleRightCurlyAnonymousClass {

    private Runnable instanceRunnable = new Runnable() {
        @Override
        public void run() {
            int x = 1;
        }
    };

    private Comparator<String> instanceComparator = new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    };

    void method() {
        Runnable localRunnable = new Runnable() {
            @Override
            public void run() {
                int y = 2;
            }
        };
        localRunnable.run();

        Thread thread = new Thread() {
            @Override
            public void run() {
                int z = 3;
            }
        };
        thread.start();
    }

    Object create() {
        return new Object() {
            @Override
            public String toString() {
                return "anon";
            }
        };
    }
}
