/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingComma {

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

    enum Foo23 {
        A,B{} // ok
    }

    enum Foo24 {
        A,B(){ public String toString() { return "";}} // ok
    }

    enum Foo25 {
        A,B(){ public String toString() { return "";}}, // violation
    }

    enum Foo26 {
        A,B(), // violation
    }

    enum Foo27 {
        A,B{}, // violation
    }

    enum Foo28 {
        A,B(); // ok
    }

    enum Foo29 {
        A,B{}; // ok
    }

    enum Foo30 {
        A,B(){ public String toString() { return "";}}; // ok
    }

    enum Foo31 {
        A,B(),; // violation
    }

    enum Foo32 {
        A,B{},; // violation
    }

    enum Foo33 {
        A,B(){ public String toString() { return "";}},; // violation
    }

    enum Foo34 {
        A, B, C; // ok
        enum NestedFoo1 {
            First, Second, Third, // violation
        }
    }

    enum Foo35 {
        A, B, C; // ok
        enum NestedFoo1 {
            First, Second, Third,; // violation
        }
    }

    enum Foo36 {
        A, B, C; // ok
        enum NestedFoo1 {
            First, Second, Third; // ok
        }
    }

    enum Foo37 {
        A, B; // ok
        Foo37() {}
    }

    enum Foo38 {
        A, B,; // violation
        Foo38() {};
    }

    public enum Foo39 {
        ONE_ONE(1,1),
        ONE_TWO(1,2); // ok

        int major, minor;

        Foo39(int major, int minor) {
            this.major = major;
            this.minor = minor;
        }
    }

    public enum Foo40 {
        ONE_ONE(1,1),
        ONE_TWO(1,2),; // violation

        int major, minor;

        Foo40(int major, int minor) {
            this.major = major;
            this.minor = minor;
        }
    }

    public enum Foo41 {
        ONE_ONE (1, 1) {
            @Override
            public void someFunction() {
            }
        },
        ONE_TWO (1, 2) {
            @Override
            public void someFunction(){
            }
        },; // violation

        int major, minor;

        Foo41 (int major, int minor) {
            this.major = major;
            this.minor = minor;
        }

        public void someFunction(){
            major = 1;
        }
    }

    public enum Foo42 {
        ONE_ONE (1, 1) {
            @Override
            public void someFunction() {
            }
        },
        ONE_TWO (1, 2) {
            @Override
            public void someFunction(){
            }
        }; // ok

        int major, minor;

        Foo42 (int major, int minor) {
            this.major = major;
            this.minor = minor;
        }

        public void someFunction(){
            major = 1;
        }
    }

    enum Foo43 {
        A,B(){ public String toString() { return "";}}; // ok
        interface SomeInterface {}
    }

    enum Foo44 {
        A,B(){ public String toString() { return "";}},; // violation
        interface SomeInterface {}
    }

    enum Foo45 {
        , // violation
    }
    enum Foo46 {
        ,; // violation
    }
}
