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

public class InputWhitespaceBeforeEmptyBodyTypes {

    class InnerClass{}
    // violation above, 'Whitespace is not present before the empty body of 'InnerClass''

    interface InnerInterface{}
    // violation above, 'Whitespace is not present before the empty body of 'InnerInterface''

    enum InnerEnum{}
    // violation above, 'Whitespace is not present before the empty body of 'InnerEnum''

    record InnerRecord(){}
    // violation above, 'Whitespace is not present before the empty body of 'InnerRecord''

    @interface InnerAnnotation{}
    // violation above, 'Whitespace is not present before the empty body of 'InnerAnnotation''

    class ValidClass {}

    interface ValidInterface {}

    enum ValidEnum {}

    record ValidRecord() {}

    @interface ValidAnnotation {}
}
