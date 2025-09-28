/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = ELLIPSIS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

class InputNoWhitespaceBeforeEllipsis {

    @Target(ElementType.TYPE_USE)
    @interface A {}

    @Target(ElementType.TYPE_USE)
    @interface B {}

    @Target(ElementType.TYPE_USE)
    @interface C {}

    void vararg1(@A String @C [] @B ... arg) {}
    // violation above ''...' is preceded with whitespace'

    void vararg2(@A String @C []    ... arg) {}
    // violation above ''...' is preceded with whitespace'

    void vararg3(@A String    [] @B ... arg) {}
    // violation above ''...' is preceded with whitespace'

    void vararg4(   String    [] @B ... arg) {}
    // violation above ''...' is preceded with whitespace'
}
