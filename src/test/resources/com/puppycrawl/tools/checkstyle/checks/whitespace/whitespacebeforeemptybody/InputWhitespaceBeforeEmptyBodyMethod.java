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

public class InputWhitespaceBeforeEmptyBodyMethod {

    void method1(){} // violation 'Whitespace is not present before the empty body of 'method1''

    public void method2(){}
    // violation above, 'Whitespace is not present before the empty body of 'method2''

    private static void method3(){}
    // violation above, 'Whitespace is not present before the empty body of 'method3''

    protected final void method4(){}
    // violation above, 'Whitespace is not present before the empty body of 'method4''

    synchronized void method5(){}
    // violation above, 'Whitespace is not present before the empty body of 'method5''

    void validMethod() {}

    public void validMethod2() {}

    private static void validMethod3() {}

    void method()
{}
}
