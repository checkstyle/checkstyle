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

class InputWhitespaceBeforeEmptyBodySynchronized {

    private final Object lock = new Object();

    void test() {

        // violation below 'Whitespace is not present before the empty body of 'synchronized''
        synchronized (lock){}

        synchronized (lock) {}

        synchronized (lock) {
            critical();
        }

        synchronized (this){
            // comment inside
        }
    }

    void critical() {}
}
