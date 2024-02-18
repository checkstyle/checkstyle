/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;

public class InputGenericWhitespaceAfterNew {
    public class Inner<T> { }

    Object obj1 = new <String>Object();
    Object obj2 = new <String> Object(); // violation, ''>' is followed by whitespace.'

    void method() {
        new <@A Inner>Inner();
        new< @A Inner > Inner (); // 4 violations
        new <@A Inner> @A Inner<@A Inner>(); // violation, ''>' is followed by whitespace.'
    }

    void popCan() {
        Can.Pop cannot = new <String>Can.Pop();
        Can.Pop burstCan = new< String > Can.Pop(); // 4 violations
    }

    @Target({TYPE_USE}) @interface A {}
}

class Can {
    static class Pop {}
}
