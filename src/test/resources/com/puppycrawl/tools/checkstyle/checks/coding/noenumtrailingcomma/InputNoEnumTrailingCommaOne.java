/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingCommaOne {

    enum Foo1 {
        FOO,
        BAR; // ok
    }

    enum Foo2 {
        FOO,
        BAR // ok
    }

    enum Foo3 {
        FOO,
        BAR, // violation
    }

    enum Foo4 {
        FOO,
        BAR, // violation
        ;
    }

    enum Foo5 {
        FOO,
        BAR,; // violation
    }

    enum Foo6 { FOO, BAR,; } // violation

    enum Foo7 { FOO, BAR, } // violation

    enum Foo8 {
        FOO,
        BAR // ok
        ;
    }

    enum Foo9 { FOO, BAR; } // ok

    enum Foo10 { FOO, BAR } // ok

    enum Foo11 {} // ok

    enum Foo12 { FOO, } // violation

    enum Foo13 { FOO } // ok

    enum Foo14 {
        FOO, // violation
    }

    enum Foo15 {
        FOO // ok
    }

    enum Foo16 {
    } // ok

    enum Foo17 { FOO; } // ok

    enum Foo18 {
        FOO; // ok
    }

    enum Foo19 {
        FOO
        , // violation
    }

    enum Foo20 {
        FOO
        ; // ok
    }

    public enum Foo21
    {
        FIRST_CONSTANT,
        SECOND_CONSTANT; // ok

        public void someMethod() {
        }
    }

    enum Foo22 {
        A,B() // ok
    }
}
