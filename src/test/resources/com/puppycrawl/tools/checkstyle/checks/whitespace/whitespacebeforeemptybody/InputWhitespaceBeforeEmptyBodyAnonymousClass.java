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

public class InputWhitespaceBeforeEmptyBodyAnonymousClass {

    abstract class Anonymous {}

    // violation below 'Whitespace is not present before the empty body of 'anonymous class''
    Anonymous anon = new Anonymous(){};

    Anonymous validAnon = new Anonymous() {};

    Anonymous nonEmpty = new Anonymous(){
        void method() {}
    };

    Object obj = new Object(){
        // comment inside
    };
}
