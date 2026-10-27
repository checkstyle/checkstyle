/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, CLASS_DEF, INTERFACE_DEF, \
         RECORD_DEF, ENUM_DEF, ANNOTATION_DEF, LITERAL_NEW, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_SYNCHRONIZED, LITERAL_SWITCH, LAMBDA, LITERAL_DO, LITERAL_IF, LITERAL_ELSE


*/

package org.checkstyle.suppressionxpathfilter.whitespace.whitespacebeforeemptybody;

public class InputXpathWhitespaceBeforeEmptyBodyLoop {

    void method() {
        for (int i = 0; i < 1; i++){ // violation "'{' is not preceded with whitespace."
           // comment
        }
    }
}
