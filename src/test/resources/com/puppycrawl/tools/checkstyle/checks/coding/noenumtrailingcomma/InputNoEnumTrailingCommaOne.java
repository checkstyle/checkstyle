/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingCommaOne {

    enum Foo1 {
        FOO,
        BAR;
    }

    enum Foo2 {
        FOO,
        BAR
    }

    enum Foo3 {
        FOO,
        BAR, // violation 'Enum should not contain trailing comma.'
    }

    enum Foo4 {
        FOO,
        BAR, // violation 'Enum should not contain trailing comma.'
        ;
    }

    enum Foo5 {
        FOO,
        BAR,; // violation 'Enum should not contain trailing comma.'
    }

    enum Foo6 { FOO, BAR,; } // violation 'Enum should not contain trailing comma.'

    enum Foo7 { FOO, BAR, } // violation 'Enum should not contain trailing comma.'

    enum Foo8 {
        FOO,
        BAR
        ;
    }

    enum Foo9 { FOO, BAR; }

    enum Foo10 { FOO, BAR }

    enum Foo11 {}

    enum Foo12 { FOO, } // violation 'Enum should not contain trailing comma.'

    enum Foo13 { FOO }

    enum Foo14 {
        FOO, // violation 'Enum should not contain trailing comma.'
    }

    enum Foo15 {
        FOO
    }

    enum Foo16 {
    }

    enum Foo17 { FOO; }

    enum Foo18 {
        FOO;
    }

    enum Foo19 {
        FOO
        , // violation 'Enum should not contain trailing comma.'
    }

    enum Foo20 {
        FOO
        ;
    }

    public enum Foo21
    {
        FIRST_CONSTANT,
        SECOND_CONSTANT;

        public void someMethod() {
        }
    }

    enum Foo22 {
        A,B()
    }
}
