/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, CLASS_DEF, INTERFACE_DEF, \
         RECORD_DEF, ENUM_DEF, ANNOTATION_DEF, LITERAL_NEW, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_SYNCHRONIZED, LITERAL_SWITCH, LAMBDA, LITERAL_DO, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyDefault {

    void methdod() {

        boolean flag = true;

        while (flag) {
            flag = false;
        }

        // violation below ''{' is not preceded with whitespace'
        for (int i = 0; i < 1; i++){ /* comment */ }

        // violation 2 lines below ''{' is not preceded with whitespace'
        // violation 2 lines below ''{' is not preceded with whitespace'
        try{
        } catch (Exception e){
        } finally{
        }
        // violation 2 lines above ''{' is not preceded with whitespace'

        // violation below ''{' is not preceded with whitespace'
        synchronized (this){
        }

        int x = 0;

        // violation below ''{' is not preceded with whitespace'
        switch (x){
        }

        Object object = new Object();
    }
}
