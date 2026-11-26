/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, CLASS_DEF, INTERFACE_DEF, \
         RECORD_DEF, ENUM_DEF, ANNOTATION_DEF, LITERAL_NEW, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, \
         LITERAL_SYNCHRONIZED, LITERAL_SWITCH, LAMBDA, LITERAL_DO, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodySupplementaryCharacters {

    class 𝒜 {}

    class 𝒜𝒞  {}

    class 𝒟{} // violation ''{' is not preceded with whitespace'

    interface 𝒞 {}

    interface 𝒢{} // violation ''{' is not preceded with whitespace'

    enum 𝒵 {}

    enum 𝒜𝒵{} // violation ''{' is not preceded with whitespace'

    @interface 𝒞𝒟 {}

    @interface 𝒟𝒵{} // violation ''{' is not preceded with whitespace'

    class Extended extends 𝒜 {}

    class ExtendedNoSpace implements 𝒞{} // violation ''{' is not preceded with whitespace'

    class Control {}

    class ControlNoSpace{} // violation ''{' is not preceded with whitespace'
}
