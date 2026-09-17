/*
WhitespaceBeforeEmptyBody
tokens = CLASS_DEF, INTERFACE_DEF, RECORD_DEF, ENUM_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyTypes {

    class C{} // violation ''{' is not preceded with whitespace'

    class D extends C {
        int x = 0;
    }

    // violation below ''{' is not preceded with whitespace'
    class E<T> extends C implements A{}

    class G<T> extends C {
        T field;
        void foo() { }
    }

    interface A
{ }

    interface B{ // violation ''{' is not preceded with whitespace'
        /* Comment */
    }

    interface F<T> extends A{
        void foo();
    }

    record Record(int x){} // violation ''{' is not preceded with whitespace'

    record Record2<T>(T x) implements A {
        void foo() { }
    }

    enum Enum{
        VALUE
    }

    // violation below ''{' is not preceded with whitespace'
    enum N{
        // comment
    }

    enum S implements A{
        SPRING, SUMMER, AUTUMN, WINTER;
    }

    // violation below ''{' is not preceded with whitespace'
    @interface Foo{}

    @interface Bar{
        String value();
    }

    class M implements A,
            B{} // violation ''{' is not preceded with whitespace'

    interface PartialFunction<T, R> {}

    interface Case<T, R> extends PartialFunction<T, R> {
    }
}
