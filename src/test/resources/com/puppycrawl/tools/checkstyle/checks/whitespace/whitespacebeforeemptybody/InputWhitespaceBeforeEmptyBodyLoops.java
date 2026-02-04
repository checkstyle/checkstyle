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

public class InputWhitespaceBeforeEmptyBodyLoops {
    void test() {
        boolean b = true;
        while (b){} // violation 'Whitespace is not present before the empty body of 'while''
        for (int i = 0; i < 1; i++){}
        // violation above, 'Whitespace is not present before the empty body of 'for''
        do{} while (b); // violation 'Whitespace is not present before the empty body of 'do''

        while (b) {}
        for (int i = 0; i < 1; i++) {}
        do {} while (b);
    }
}
