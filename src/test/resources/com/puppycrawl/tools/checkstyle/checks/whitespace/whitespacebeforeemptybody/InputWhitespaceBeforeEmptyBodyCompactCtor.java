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

public class InputWhitespaceBeforeEmptyBodyCompactCtor {

    record MyRecord(String name) {
        // violation below 'Whitespace is not present before the empty body of 'MyRecord''
        public MyRecord{}
    }

    record ValidRecord(String name) {
        public ValidRecord {}
    }

    record NonEmptyRecord(String name) {
        public NonEmptyRecord{
            name = name.trim();
        }
    }
}
