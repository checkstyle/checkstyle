/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, \
         CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF, \
         LITERAL_WHILE, LITERAL_FOR, LITERAL_DO, \
         STATIC_INIT, \
         LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_SYNCHRONIZED, LITERAL_SWITCH, \
         LAMBDA, LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

class InputWhitespaceBeforeEmptyBodyFinally {

    void test() throws Exception {

        try {
            riskyOp();
        }

        // violation below 'Whitespace is not present before the empty body of 'finally''
        finally{}

        try {
            riskyOp();
        }

        finally {}

        try {
            riskyOp();
        }

        finally {
            cleanup();
        }

        try {}
        catch (Exception e) {}
        finally{ // cleanup
        }
    }

    void riskyOp() throws Exception {}

    void cleanup() {}
}
